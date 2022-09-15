package iv.game.guitarhelper.di

import be.tarsos.dsp.AudioDispatcher
import dagger.Component
import iv.game.guitarhelper.di.module.AudioAnalysisModule
import iv.game.guitarhelper.di.module.ViewModelModule
import iv.game.guitarhelper.viewmodel.AudioRecordViewModel
import iv.game.guitarhelper.viewmodel.LearnNoteViewModel

@Component(
    modules = [AudioAnalysisModule::class, ViewModelModule::class]
)
interface GlobalComponent {

    fun defaultAudioDispatcher(): AudioDispatcher

    // ViewModels
    fun audioRecordViewModel(): AudioRecordViewModel
    fun learnNoteViewModel(): LearnNoteViewModel
}