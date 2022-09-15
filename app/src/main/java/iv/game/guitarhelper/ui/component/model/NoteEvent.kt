package iv.game.guitarhelper.ui.component.model

import iv.game.guitarhelper.audio.note.Note
import kotlin.math.abs


sealed class NoteEvent {
    abstract val target: Note
}

/**
 * Информация о том какая нота определилась
 */
data class NoteInfo(
    override val target: Note,
    val detected: Boolean,
    val rate: Float
): NoteEvent() {

    fun deviation(): Float = abs(this.target.rate-rate)
}

data class CurrentNote(
    override val target: Note
): NoteEvent()
