package iv.game.guitarhelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iv.game.guitarhelper.R
import iv.game.guitarhelper.databinding.FragmentHomeBinding
import iv.game.guitarhelper.ui.addFragment
import iv.game.guitarhelper.ui.component.adapter.HomeMenuItemAdapter
import iv.game.guitarhelper.ui.component.model.HomeMenuItem

class HomeFragment: Fragment() {

    // Views
    private lateinit var menuRecyclerView: RecyclerView

    // Components
    private lateinit var homeMenuItemAdapter: HomeMenuItemAdapter

    // Utils
    private val menuItemListener = { f: Fragment ->
        this.parentFragmentManager.addFragment(R.id.page_container, f, "menu-item", this, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
            .binding()

        homeMenuItemAdapter = HomeMenuItemAdapter(menuItems()) { menuItemListener.invoke(it) }
        menuRecyclerView.adapter = homeMenuItemAdapter
        menuRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }

    // ---
    // PRIVATE
    // ---

    private fun View.binding() = this.apply {
        FragmentHomeBinding.bind(this)
            .apply { this@HomeFragment.menuRecyclerView = menuListView }
    }

    private fun menuItems(): List<HomeMenuItem> = listOf(
        HomeMenuItem(requireContext(), R.string.menu_item_1, R.drawable.ic_music_note, R.color.green_light) { AudioRecordFragment() },
        HomeMenuItem(requireContext(), R.string.menu_item_2, R.drawable.ic_columns, R.color.yellow_light) { AudioRecordFragment() },
        HomeMenuItem(requireContext(), R.string.menu_item_3, R.drawable.ic_sk_key, R.color.orange_light) { AudioRecordFragment() }
    )
}