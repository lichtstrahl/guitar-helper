package iv.game.guitarhelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.FragmentMenuBinding

class MenuFragment: Fragment() {

    // Views
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var pageContainer: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.binding()
        initClickListeners()
        openMenuPage(bottomNavigationView.selectedItemId)

        return view
    }

    // ---
    // PRIVATE
    // ---

    private fun View.binding() = FragmentMenuBinding.bind(this)
        .apply { bottomNavigationView = bottomNavigation }
        .apply { this@MenuFragment.pageContainer = this.pageContainer }

    private fun initClickListeners() {
        bottomNavigationView.setOnItemSelectedListener {
            openMenuPage(it.itemId)
            true
        }
    }

    /**
     * Открытие конкретной страницы меню
     * @param menuItemId Идентификатор выбранного элемента
     */
    private fun openMenuPage(menuItemId: Int) = when (menuItemId) {
        R.id.bottom_menu_home -> this.requireActivity()
            .supportFragmentManager
            .openMenuPage(HomeFragment())
        R.id.bottom_menu_settings -> this.requireActivity()
            .supportFragmentManager
            .openMenuPage(SettingsFragment())
        else ->  throw IllegalStateException("Illegal menu item: $menuItemId")
    }

    private fun FragmentManager.openMenuPage(fragment: Fragment) = this
        .beginTransaction()
        .add(R.id.page_container, fragment, null)
        .commit()
}