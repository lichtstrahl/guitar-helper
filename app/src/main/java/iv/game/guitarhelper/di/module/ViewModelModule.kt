package iv.game.guitarhelper.di.module

import be.tarsos.dsp.AudioDispatcher
import dagger.Module
import dagger.Provides
import iv.game.guitarhelper.audio.processor.NoteAudioProcessor
import iv.game.guitarhelper.viewmodel.AudioRecordViewModel
import iv.game.guitarhelper.viewmodel.LearnNoteViewModel

@Module(includes = [AudioAnalysisModule::class])
class ViewModelModule {

    @Provides
    fun audioRecordViewModel(audioDispatcher: AudioDispatcher, noteAudioProcessor: NoteAudioProcessor): AudioRecordViewModel = AudioRecordViewModel(audioDispatcher, noteAudioProcessor)

    @Provides
    fun learnNoteViewModel(audioDispatcher: AudioDispatcher, noteAudioProcessor: NoteAudioProcessor): LearnNoteViewModel = LearnNoteViewModel(audioDispatcher, noteAudioProcessor)
}