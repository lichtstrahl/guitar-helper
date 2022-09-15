package iv.game.guitarhelper.viewmodel.model

sealed class GameResult {
}

object Win: GameResult() {

}

object Defeat: GameResult() {

}