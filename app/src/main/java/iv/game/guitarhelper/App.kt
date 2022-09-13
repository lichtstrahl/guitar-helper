package iv.game.guitarhelper

import android.app.Application
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    // ---
    // PRIVATE
    // ---

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}