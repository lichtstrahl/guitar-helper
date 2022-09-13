package iv.game.guitarhelper.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.*
import timber.log.Timber

// TODO Пока отключил проверку разрешений. Подразумевается что здесь они уже 100% выданы
@SuppressLint("MissingPermission")
class IvAudioRecorder(
) {

    private var recorder: AudioRecord? = null
    private var scope: CoroutineScope? = null

    companion object {
        private const val SAMPLE_RATE_HZ = 44100
        private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        private const val ENCODING = AudioFormat.ENCODING_PCM_16BIT
        private val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_HZ, CHANNEL_CONFIG, ENCODING)
    }

    fun start() {
        Timber.i("Start")
        val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_HZ, CHANNEL_CONFIG, ENCODING, BUFFER_SIZE)
        recorder.startRecording()
        this.scope = CoroutineScope(Dispatchers.IO + Job()).apply {
            this.launch {
                val data = ByteArray(BUFFER_SIZE/2)

                while (true) {
                    val read = recorder.read(data, 0, data.size)
                    Timber.d("Read $read bytes")
                    Timber.d("[${data[0]},${data[1]},${data[2]},${data[3]},${data[4]}]")
                }
            }
        }

        this.recorder = recorder
    }

    fun isActive(): Boolean = recorder != null

    fun stop() {
        Timber.i("Stop")
        scope?.cancel()
        scope = null
        recorder?.let {
            it.stop()
            it.release()
        }
        recorder = null
    }
}