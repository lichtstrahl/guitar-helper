package iv.game.guitarhelper.audio.processor

import androidx.lifecycle.MutableLiveData
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.Oscilloscope
import timber.log.Timber

class AmplitudeAudioProcessor(
    private val amplitude: MutableLiveData<Double>
): Oscilloscope.OscilloscopeEventHandler {

    val processor = Oscilloscope(this)

    // Найти среднюю амплитуду
    override fun handleEvent(data: FloatArray, event: AudioEvent) {
        val avgAmplitude = data.filterIndexed { index, _ -> index % 2 == 0 }.average()
        Timber.i("Avg amplitude: $avgAmplitude")
        amplitude.postValue(avgAmplitude)
    }
}