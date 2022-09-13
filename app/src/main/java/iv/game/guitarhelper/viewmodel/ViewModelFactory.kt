package iv.game.guitarhelper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        AudioRecordViewModel::class.java -> AudioRecordViewModel()
        else -> throw IllegalStateException("Unknown viewModel $modelClass")
    } as T

}