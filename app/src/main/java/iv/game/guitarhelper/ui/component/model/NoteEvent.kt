package iv.game.guitarhelper.ui.component.model

import iv.game.guitarhelper.audio.note.Note
import kotlin.math.abs

data class NoteEvent(
    val target: Note,
    val detected: Boolean,
    val rate: Float,
    val deviation: Float = abs(target.rate-rate)
)
