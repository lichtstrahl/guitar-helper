package iv.game.guitarhelper.audio

import android.media.AudioFormat
import android.media.AudioRecord

object AudioConfig {
    // Набор стандартных настроек, которые под капотом используются в TARSOS
    const val SAMPLE_RATE_HZ = 44100
    const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
    const val ENCODING = AudioFormat.ENCODING_PCM_16BIT
    val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_HZ, CHANNEL_CONFIG, ENCODING)
}