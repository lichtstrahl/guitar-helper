package iv.game.guitarhelper.viewmodel

import android.annotation.SuppressLint
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.Oscilloscope
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.synthesis.AmplitudeLFO
import iv.game.guitarhelper.audio.AudioConfig.BUFFER_SIZE
import iv.game.guitarhelper.audio.AudioConfig.SAMPLE_RATE_HZ
import iv.game.guitarhelper.audio.note.Note
import iv.game.guitarhelper.audio.processor.AmplitudeAudioProcessor
import iv.game.guitarhelper.audio.processor.NoteAudioProcessor
import iv.game.guitarhelper.ui.component.model.NoteEvent
import kotlinx.coroutines.*
import timber.log.Timber

// TODO Пока отключил проверку разрешений. Подразумевается что здесь они уже 100% выданы
@SuppressLint("MissingPermission")
class AudioRecordViewModel: ViewModel() {


    private var scope: CoroutineScope? = null
    private var audioDispatcher: AudioDispatcher? = null

    // LiveData
    private val internalNotes = MutableLiveData<NoteEvent>()
    val notes: LiveData<NoteEvent> = internalNotes

    private val internalAmplitude = MutableLiveData<Double>()
    val amplitude: LiveData<Double> = internalAmplitude

    fun startRecord() {
        Timber.i("Start record")


        MediaRecorder().maxAmplitude

        val audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE_HZ, BUFFER_SIZE, BUFFER_SIZE/2)
            .apply { addAudioProcessor(NoteAudioProcessor(internalNotes)) }
            .apply { addAudioProcessor(AmplitudeAudioProcessor(internalAmplitude).processor) }

        this.scope = CoroutineScope(Dispatchers.IO + Job()).apply {
            this.launch {
                audioDispatcher.run()
            }
        }

        this.audioDispatcher = audioDispatcher
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared")
        audioDispatcher?.stop()
        audioDispatcher = null
        scope?.cancel()
        scope = null
    }
}