package iv.game.guitarhelper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iv.game.guitarhelper.App

class ViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        AudioRecordViewModel::class.java -> App.globalComponent.audioRecordViewModel()
        LearnNoteViewModel::class.java -> App.globalComponent.learnNoteViewModel()
        else -> throw IllegalStateException("Unknown viewModel $modelClass")
    } as T

}