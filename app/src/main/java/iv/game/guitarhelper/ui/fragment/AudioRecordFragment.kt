package iv.game.guitarhelper.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iv.game.guitarhelper.R
import iv.game.guitarhelper.audio.IvAudioRecorder
import timber.log.Timber

class AudioRecordFragment: Fragment() {

    // Components
    private val audioRecorder = IvAudioRecorder()
    private val timer = object : CountDownTimer(60_000, 200) {
        override fun onTick(millisUntilFinished: Long) {
            Timber.i("tick")
        }

        override fun onFinish() {
            Timber.i("onFinish")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_audio_record, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        audioRecorder.start()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        audioRecorder.stop()
        timer.cancel()
    }

}