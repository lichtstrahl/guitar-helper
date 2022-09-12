package iv.game.guitarhelper.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.ActivityMainBinding
import iv.game.guitarhelper.ui.fragment.MenuFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, MenuFragment())
            .commit()
    }
}