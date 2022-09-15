package iv.game.guitarhelper.ui.fragment.game

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.FragmentGameLearnNoteBinding
import iv.game.guitarhelper.ui.component.model.NoteEvent
import iv.game.guitarhelper.ui.fragment.HomeFragment
import iv.game.guitarhelper.viewmodel.LearnNoteViewModel
import iv.game.guitarhelper.viewmodel.ViewModelFactory
import iv.game.guitarhelper.viewmodel.model.Defeat
import iv.game.guitarhelper.viewmodel.model.GameResult
import iv.game.guitarhelper.viewmodel.model.Win
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class LearnNoteFragment: Fragment() {

    // Views
    private lateinit var gameTimerView: TextView
    private lateinit var countNoteView: TextView
    private lateinit var noteView: TextView
    private lateinit var slideBackground: ConstraintLayout
    private lateinit var startTimerView: TextView
    private lateinit var backArrowButton: ImageView
    private lateinit var againButton: ImageView

    // ViewModel
    private lateinit var learnNoteViewModel: LearnNoteViewModel

    // Args
    private var seconds: Int = -1
    private var countNotes: Int = -1

    companion object {
        private const val ARG_SECONDS = "arg:seconds"
        private const val ARG_NOTES = "arg:count-notes"

        fun getInstance(seconds: Int, notes: Int) = LearnNoteFragment().apply {
            this.arguments = Bundle().apply {
                this.putInt(ARG_SECONDS, seconds)
                this.putInt(ARG_NOTES, notes)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_game_learn_note, container, false)
            .binding()

        arguments?.let {
            this.seconds = it.getInt(ARG_SECONDS)
            this.countNotes = it.getInt(ARG_NOTES)
        }

        initView()

        learnNoteViewModel = ViewModelProvider(this, ViewModelFactory()).get()
        learnNoteViewModel.startSeconds.observe(this.viewLifecycleOwner, this::drawStartTimer)
        learnNoteViewModel.hideSlide.observe(this.viewLifecycleOwner, this::drawSlide)
        learnNoteViewModel.gameTime.observe(this.viewLifecycleOwner, this::drawGameTimer)
        learnNoteViewModel.gameResult.observe(this.viewLifecycleOwner, this::drawGameResult)
        learnNoteViewModel.currentNote.observe(this.viewLifecycleOwner, this::drawCurrentNote)
        learnNoteViewModel.countNotes.observe(this.viewLifecycleOwner, this::drawCountNotes)

        return view
    }

    override fun onStart() {
        super.onStart()
        learnNoteViewModel.runStartTimer()
        learnNoteViewModel.runGame(seconds, countNotes)
    }

    // ---
    // PRIVATE
    // ---

    private fun View.binding() = this.apply {
        FragmentGameLearnNoteBinding.bind(this).apply {
            this@LearnNoteFragment.noteView = noteView
            this@LearnNoteFragment.slideBackground = slideBackground
            this@LearnNoteFragment.startTimerView = startTimerView
            this@LearnNoteFragment.gameTimerView = gameTimerView
            this@LearnNoteFragment.countNoteView = noteCountView
            this@LearnNoteFragment.againButton = againButton
            this@LearnNoteFragment.backArrowButton = backArrowButton
        }
    }

    private fun initView() {
        this.noteView.text = "?"
        drawTimer(seconds*1000L)

        startTimerView.setTextColor(resources.getColor(R.color.red_light, requireContext().theme))
        startTimerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.single_note))
        backArrowButton.visibility = View.INVISIBLE
        backArrowButton.visibility = View.INVISIBLE
    }

    private fun drawGameResult(result: GameResult) {
        drawSlide(View.VISIBLE)

        when (result) {
            Win -> {
                startTimerView.setText(R.string.win)
                startTimerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.single_note))
                startTimerView.setTextColor(resources.getColor(R.color.red_light, requireContext().theme))

                backArrowButton.visibility = View.VISIBLE
            }

            Defeat -> {
                startTimerView.setText(R.string.defeat)
                startTimerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                startTimerView.setTextColor(resources.getColor(R.color.black, requireContext().theme))

                backArrowButton.visibility = View.VISIBLE
            }
        }

        // parent - Фргамент с меню
        backArrowButton.setOnClickListener {
            val homeFragment = this.parentFragmentManager.findFragmentByTag(HomeFragment.TAG)!!
            this.parentFragmentManager.beginTransaction()
                .show(homeFragment)
                .remove(this)
                .commit()
        }
    }

    private fun drawSlide(value: Int) = if (value == View.INVISIBLE) {
        slideBackground.animate()
            .alpha(0.0f)
            .setDuration(700)
            .start()
    } else {
        slideBackground.animate()
            .alpha(1.0f)
            .setDuration(700)
            .start()
    }

    private fun drawCurrentNote(noteEvent: NoteEvent) {
        noteView.animate()
            .alpha(0.0f)
            .setDuration(300)
            .start()

        noteView.text = noteEvent.target.name

        noteView.animate()
            .alpha(1.0f)
            .setDuration(300)
            .start()
    }

    private fun drawGameTimer(ms: Long) {
        drawTimer(ms)
    }

    private fun drawCountNotes(n: Int) {
        this.countNoteView.text = resources.getString(R.string.count_not_format).format(n, countNotes)
    }

    private fun drawStartTimer(value: Int) {
        startTimerView.text = value.toString()
    }

    private fun drawTimer(ms: Long, format: String = "HH:mm:ss.S") {
        this.gameTimerView.text = DateTimeFormatter.ofPattern(format)
            .format(Instant.ofEpochMilli(ms).atZone(ZoneOffset.UTC).toLocalTime())
    }
}
