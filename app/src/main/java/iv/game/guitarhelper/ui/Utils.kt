package iv.game.guitarhelper.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import iv.game.guitarhelper.R

fun FragmentManager.addFragment(@IdRes containerId: Int, fragment: Fragment, tag: String?, hideFragment: Fragment? = null, enableAnimation: Boolean = true) = this
    .beginTransaction()
    .apply { if (enableAnimation) this.setCustomAnimations(R.anim.open_fragment, R.anim.close_fragment, R.anim.open_fragment, R.anim.close_fragment) }
    .add(containerId, fragment, tag)
    .apply { hideFragment?.let { this.hide(it) } }
    .commit()