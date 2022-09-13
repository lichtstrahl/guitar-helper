package iv.game.guitarhelper.audio.processor

import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.AudioProcessor
import timber.log.Timber

class LogAudioProcessor: AudioProcessor {

    override fun process(audioEvent: AudioEvent): Boolean {
        Timber.i("BSPL: ${audioEvent.getdBSPL()}, progress: ${audioEvent.progress}")
        return true
    }

    override fun processingFinished() {
        Timber.i("Finish")
    }
}