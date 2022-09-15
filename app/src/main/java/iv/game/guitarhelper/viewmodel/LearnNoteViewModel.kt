package iv.game.guitarhelper.viewmodel

import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import be.tarsos.dsp.AudioDispatcher
import iv.game.guitarhelper.audio.note.Note
import iv.game.guitarhelper.audio.processor.NoteAudioProcessor
import iv.game.guitarhelper.ui.component.model.CurrentNote
import iv.game.guitarhelper.ui.component.model.NoteEvent
import iv.game.guitarhelper.ui.component.model.NoteInfo
import iv.game.guitarhelper.viewmodel.model.Defeat
import iv.game.guitarhelper.viewmodel.model.GameResult
import iv.game.guitarhelper.viewmodel.model.Win
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LearnNoteViewModel @Inject constructor(
    private val audioDispatcher: AudioDispatcher,
    private val noteAudioProcessor: NoteAudioProcessor
): ViewModel() {

    private var startTimer: StartTimer? = null
    private var gameTimer: GameTimer? = null


    private val internalStartSeconds = MutableLiveData<Int>()
    val startSeconds: LiveData<Int> = internalStartSeconds

    private val internalHideSlide = MutableLiveData<Int>()
    val hideSlide: LiveData<Int> = internalHideSlide

    private val internalGameTime = MutableLiveData<Long>()
    val gameTime: LiveData<Long> = internalGameTime

    private val internalGameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> = internalGameResult

    private val internalCurrentNote = MutableLiveData<NoteEvent>()
    val currentNote: LiveData<NoteEvent> = internalCurrentNote

    private val internalCountNotes = MutableLiveData<Int>()
    val countNotes: LiveData<Int> = internalCountNotes

    private val noteObservers = mutableListOf<Observer<NoteInfo>>()

    fun runStartTimer() {
        startTimer?.cancel()
        startTimer = StartTimer(3)
        startTimer?.start()
    }

    fun runGame(seconds: Int, countNotes: Int) {
        gameTimer?.cancel()
        gameTimer = GameTimer(seconds)


        val notes = randomNotes(countNotes)
        val noteObserver = Observer<NoteInfo> {
            Timber.d("target: ${internalCurrentNote.value!!.target} note: ${it.target.name} ")
            if (internalCurrentNote.value!!.target == it.target) {
                if (notes.isNotEmpty()) {
                    notes.nextNote(countNotes)
                } else {
                    internalGameResult.postValue(Win)
                    stopGame()
                    stopRecord()
                }

            }
        }
        noteObservers.add(noteObserver)
        noteAudioProcessor.notes.observeForever(noteObserver)
        audioDispatcher.addAudioProcessor(noteAudioProcessor)

        notes.nextNote(countNotes)

        viewModelScope.launch {
            withContext(Dispatchers.IO) { audioDispatcher.run() }
        }
    }

    override fun onCleared() {
        super.onCleared()

        startTimer?.cancel()
        startTimer = null

        stopGame()
        stopRecord()
    }

    // ---
    // PRIVATE
    // ---

    private inner class StartTimer(seconds: Int): CountDownTimer(seconds*1000L, 1000) {

        private var s = seconds

        override fun onTick(millisUntilFinished: Long) {
            internalStartSeconds.postValue(s--)
        }

        override fun onFinish() {
            gameTimer?.start()
            internalHideSlide.postValue(View.INVISIBLE)
        }
    }

    private inner class GameTimer(seconds: Int): CountDownTimer(seconds*1000L, 100) {

        override fun onTick(millisUntilFinished: Long) {
            internalGameTime.postValue(millisUntilFinished)
        }

        override fun onFinish() {
            internalGameResult.postValue(Defeat)
        }
    }

    private fun stopGame() {
        gameTimer?.cancel()
        gameTimer = null
    }

    private fun stopRecord() {
        audioDispatcher.stop()
        noteObservers.forEach(noteAudioProcessor.notes::removeObserver)
        noteObservers.clear()
    }

    /**
     * Оповещение UI о том сколько нот сыграно и переход в ожидание следующей
     * @param countNotes Сколько нот всего
     */
    private fun Stack<Note>.nextNote(countNotes: Int) {
        internalCountNotes.postValue(countNotes-this.size)
        internalCurrentNote.postValue(CurrentNote(this.pop()))
    }

    /**
     * @return Стэк из случайных нот
     */
    private fun randomNotes(n: Int): Stack<Note> = Stack<Note>().apply {
        (1..n).forEach {
            push(Note.values().toList().shuffled().first())
        }
    }
}