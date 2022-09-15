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
import timber.log.Timber

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
        R.id.bottom_menu_home -> openMenuPage(HomeFragment(), HomeFragment.TAG)
        R.id.bottom_menu_settings -> openMenuPage(SettingsFragment(), SettingsFragment.TAG)
        else ->  throw IllegalStateException("Illegal menu item: $menuItemId")
    }

    /**
     * 1. Если выбранный фрагмент уже присутствует на вершине стека - ничего не делать
     * 2. Удаляем из стека последний фрагмент
     */
    private fun openMenuPage(fragment: Fragment, tag: String) {
        Timber.d("count fragments: ${childFragmentManager.fragments.size}")
        if (childFragmentManager.fragments.lastOrNull()?.tag != tag) {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.open_fragment, R.anim.close_fragment, R.anim.open_fragment, R.anim.close_fragment)
                .replace(R.id.page_container, fragment, tag)
                .commit()
        }
    }
}