package iv.game.guitarhelper.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import iv.game.guitarhelper.audio.processor.LogAudioProcessor
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
//        private val TARSOS_FORMAT = TarsosDSPAudioFormat(SAMPLE_RATE_HZ.toFloat(), 16, 1, true, false)
    }

    fun start() {
        Timber.i("Start")
        val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_HZ, CHANNEL_CONFIG, ENCODING, BUFFER_SIZE)
        recorder.startRecording()

        val audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE_HZ, BUFFER_SIZE, BUFFER_SIZE/2)
            .apply { addAudioProcessor(LogAudioProcessor()) }

        this.scope = CoroutineScope(Dispatchers.IO + Job()).apply {
            this.launch {
                audioDispatcher.run()
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