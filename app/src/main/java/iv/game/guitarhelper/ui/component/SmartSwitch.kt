package iv.game.guitarhelper.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import com.google.android.material.switchmaterial.SwitchMaterial
import iv.game.guitarhelper.R

class SmartSwitch(
    ctx: Context,
    attrs: AttributeSet? = null,
    private val switchMaterial: SwitchMaterial = SwitchMaterial(ctx, attrs),
    private val lst: List<String> = listOf()
): List<String> by lst {
}