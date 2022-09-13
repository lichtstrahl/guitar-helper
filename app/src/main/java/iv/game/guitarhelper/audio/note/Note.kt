package iv.game.guitarhelper.audio.note

import kotlin.math.abs

enum class Note(
//    val title: String,
    val rate: Float
) {
    e(329.63f),
    B(246.94f),
    G(196.00f),
    D(146.83f),
    A(110.00f),
    E(82.41f);

    companion object {
        private const val EPS = 10.5

        /**
         * Поиск ноты по частоте с заданной точностью
         * @return null если ноту не удалось определить
         */
        fun note(hz: Float, eps: Double = EPS): Note? = Note.values().firstOrNull { abs(it.rate-hz) < EPS }
    }


}