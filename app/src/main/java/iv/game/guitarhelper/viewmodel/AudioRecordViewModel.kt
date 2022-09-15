package iv.game.guitarhelper.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.tarsos.dsp.AudioDispatcher
import iv.game.guitarhelper.audio.processor.NoteAudioProcessor
import iv.game.guitarhelper.ui.component.model.NoteEvent
import iv.game.guitarhelper.ui.component.model.NoteInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

// TODO Пока отключил проверку разрешений. Подразумевается что здесь они уже 100% выданы
@SuppressLint("MissingPermission")
class AudioRecordViewModel @Inject constructor(
    private val audioDispatcher: AudioDispatcher,
    private val noteAudioProcessor: NoteAudioProcessor
): ViewModel() {

    // LiveData
    val notes: LiveData<NoteInfo> = noteAudioProcessor.notes

    private val internalAmplitude = MutableLiveData<Double>()
    val amplitude: LiveData<Double> = internalAmplitude

    fun startRecord() {
        Timber.i("Start record")

        audioDispatcher.addAudioProcessor(noteAudioProcessor)

        viewModelScope.launch {
            withContext(Dispatchers.IO) { audioDispatcher.run() }
        }
        Timber.i("Start record finish")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared")
        audioDispatcher.stop()
    }
}