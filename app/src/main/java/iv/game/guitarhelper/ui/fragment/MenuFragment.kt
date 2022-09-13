package iv.game.guitarhelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.FragmentMenuBinding
import iv.game.guitarhelper.ui.addFragment

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
        R.id.bottom_menu_home -> openMenuPage(HomeFragment(), "menu-home")
        R.id.bottom_menu_settings -> openMenuPage(SettingsFragment(), "menu-settings")
        else ->  throw IllegalStateException("Illegal menu item: $menuItemId")
    }

    private fun openMenuPage(fragment: Fragment, tag: String) {
        if (childFragmentManager.fragments.lastOrNull()?.tag != tag) {

            val hideFragment = childFragmentManager.fragments.lastOrNull()
            childFragmentManager.addFragment(R.id.page_container, fragment, tag, hideFragment)
        }
    }
}