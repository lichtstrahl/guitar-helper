package iv.game.guitarhelper.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iv.game.guitarhelper.R
import iv.game.guitarhelper.ui.fragment.MenuFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, MenuFragment())
            .commit()
    }
}