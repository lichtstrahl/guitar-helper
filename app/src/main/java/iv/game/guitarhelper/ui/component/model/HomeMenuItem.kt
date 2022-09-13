package iv.game.guitarhelper.ui.component.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

data class HomeMenuItem(
    val title: String,
    val drawable: Drawable,
    val color: Int,
    val fragmentSupplier: () -> Fragment
) {
    constructor(
        ctx: Context,
        @StringRes title: Int,
        @DrawableRes drawable: Int,
        @ColorRes color: Int,
        supplier: () -> Fragment
    ): this(
        ctx.getString(title),
        AppCompatResources.getDrawable(ctx, drawable)!!,
        ctx.getColor(color),
        supplier
    )
}
