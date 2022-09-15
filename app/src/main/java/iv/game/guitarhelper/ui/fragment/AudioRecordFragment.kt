package iv.game.guitarhelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.FragmentAudioRecordBinding
import iv.game.guitarhelper.ui.component.model.NoteEvent
import iv.game.guitarhelper.ui.component.model.NoteInfo
import iv.game.guitarhelper.viewmodel.AudioRecordViewModel
import iv.game.guitarhelper.viewmodel.ViewModelFactory
import kotlin.math.min

class AudioRecordFragment: Fragment() {

    // Views
    private lateinit var lastNotesView: TextView
    private lateinit var amplitudeView: TextView

    // ViewModel
    private lateinit var audioRecordViewModel: AudioRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_audio_record, container, false)
            .binding()

        audioRecordViewModel = ViewModelProvider(this, ViewModelFactory()).get()
        audioRecordViewModel.notes.observe(this.viewLifecycleOwner, this::drawNoteEvent)
        audioRecordViewModel.amplitude.observe(this.viewLifecycleOwner, this::drawAmplitude)

        return view
    }

    override fun onStart() {
        super.onStart()
        audioRecordViewModel.startRecord()
    }

    // ---
    // PRIVATE
    // ---

    private fun View.binding() = this.apply {
        FragmentAudioRecordBinding.bind(this)
            .apply { this@AudioRecordFragment.lastNotesView = lastNotesView }
            .apply { this@AudioRecordFragment.amplitudeView = amplitudeView }
    }

    private fun drawNoteEvent(event: NoteInfo) {
        val text = listOf(event.target.name) + lastNotesView.text.split(" ")
        val n = text.size
        lastNotesView.text = text.subList(0, min(5, n)).joinToString(separator = " ")
    }

    private fun drawAmplitude(amplitude: Double) {
        val text = listOf("%8.3f".format(amplitude)) + amplitudeView.text.split(" ")
        val n = text.size

        amplitudeView.text = text.subList(0, min(5, n)).joinToString(" ")
    }
}