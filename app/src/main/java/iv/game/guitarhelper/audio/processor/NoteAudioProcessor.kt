package iv.game.guitarhelper.audio.processor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.pitch.DynamicWavelet
import iv.game.guitarhelper.audio.note.Note
import iv.game.guitarhelper.ui.component.model.NoteInfo
import timber.log.Timber

/**
 * Компонент анализирует аудио-поток и определяет была ли там нота
 */
class NoteAudioProcessor(
    rate: Int,
    bufferSize: Int,
    private val threshold: Double
): AudioProcessor {

    private val internalNotes = MutableLiveData<NoteInfo>()
    val notes: LiveData<NoteInfo> = internalNotes

    private val noteAnalyzer = DynamicWavelet(rate.toFloat(), bufferSize)

    override fun process(audioEvent: AudioEvent): Boolean {

        val result = noteAnalyzer.getPitch(audioEvent.floatBuffer)

        val avgAmplitude = audioEvent.floatBuffer.average()
        if (avgAmplitude > threshold) {
            Timber.i("Avg amplitude: ${audioEvent.floatBuffer.average()}")
            Note.note(result.pitch)?.let {
                Timber.i("Find note $it")
                internalNotes.postValue(NoteInfo(it, result.isPitched, result.pitch))
            } ?: run {
                if (result.isPitched) {
                    Timber.i("not found note but pitched: Hz: ${result.pitch}")
                }
            }
        }

        return true
    }

    override fun processingFinished() {
        Timber.i("Finish")
    }
}