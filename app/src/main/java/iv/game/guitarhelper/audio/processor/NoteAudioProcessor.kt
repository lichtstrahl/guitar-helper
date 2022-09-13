package iv.game.guitarhelper.audio.processor

import androidx.lifecycle.MutableLiveData
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.pitch.DynamicWavelet
import iv.game.guitarhelper.audio.AudioConfig
import iv.game.guitarhelper.audio.note.Note
import iv.game.guitarhelper.ui.component.model.NoteEvent
import timber.log.Timber

/**
 * Компонент анализирует аудио-поток и определяет была ли там нота
 */
class NoteAudioProcessor(
    private val notes: MutableLiveData<NoteEvent>,
    rate: Int = AudioConfig.SAMPLE_RATE_HZ,
    bufferSize: Int = AudioConfig.BUFFER_SIZE
): AudioProcessor {

    private val noteAnalyzer = DynamicWavelet(rate.toFloat(), bufferSize)

    override fun process(audioEvent: AudioEvent): Boolean {

        val result = noteAnalyzer.getPitch(audioEvent.floatBuffer)

        Note.note(result.pitch)?.let {
            Timber.i("Find note $it")
            notes.postValue(NoteEvent(it, result.isPitched, result.pitch))
        } ?: run {
            if (result.isPitched) {
                Timber.i("not found note but pitched: Hz: ${result.pitch}")
            }
        }

        return true
    }

    override fun processingFinished() {
        Timber.i("Finish")
    }
}