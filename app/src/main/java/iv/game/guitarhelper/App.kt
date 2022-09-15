package iv.game.guitarhelper

import android.app.Application
import iv.game.guitarhelper.di.DaggerGlobalComponent
import iv.game.guitarhelper.di.GlobalComponent
import iv.game.guitarhelper.di.module.AudioAnalysisModule
import iv.game.guitarhelper.di.module.ViewModelModule
import timber.log.Timber

class App: Application() {

    companion object {
        lateinit var globalComponent: GlobalComponent
    }

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initDI()
    }

    // ---
    // PRIVATE
    // ---

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initDI() {
        globalComponent = DaggerGlobalComponent.builder()
            .audioAnalysisModule(AudioAnalysisModule())
            .viewModelModule(ViewModelModule())
            .build()
    }

}