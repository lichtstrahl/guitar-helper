package iv.game.guitarhelper.di.module

import android.media.AudioFormat
import android.media.AudioRecord
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import dagger.Module
import dagger.Provides
import iv.game.guitarhelper.audio.processor.NoteAudioProcessor

@Module
class AudioAnalysisModule {

    companion object {
        // Набор стандартных настроек, которые под капотом используются в TARSOS
        const val SAMPLE_RATE_HZ = 44100
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val ENCODING = AudioFormat.ENCODING_PCM_16BIT
        val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_HZ, CHANNEL_CONFIG, ENCODING)
    }

    @Provides
    fun defaultAudioDispatcher() = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE_HZ, BUFFER_SIZE, BUFFER_SIZE/2)

    @Provides
    fun noteAudioProcessor() = NoteAudioProcessor(SAMPLE_RATE_HZ, BUFFER_SIZE, 3e-5)
}