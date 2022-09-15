package iv.game.guitarhelper.audio.note

import kotlin.math.abs

enum class Note(
//    val title: String,
    val rate: Float
) {
    e0(329.63f),
    B0(246.94f),
    G0(196.00f),
    D0(146.83f),
    A0(110.00f),
    E0(82.41f);

    companion object {
        private const val EPS = 8.5

        /**
         * Поиск ноты по частоте с заданной точностью
         * @return null если ноту не удалось определить
         */
        fun note(hz: Float, eps: Double = EPS): Note? = Note.values().firstOrNull { abs(it.rate-hz) < eps }
    }


}