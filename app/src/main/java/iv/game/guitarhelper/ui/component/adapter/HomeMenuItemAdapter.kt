package iv.game.guitarhelper.ui.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import iv.game.guitarhelper.R
import iv.game.guitarhelper.ui.component.model.HomeMenuItem

class HomeMenuItemAdapter(
    private val content: List<HomeMenuItem>,
    private val clickListener: (Fragment) -> Unit
): RecyclerView.Adapter<HomeMenuItemAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {

        private val cardBackground = item.findViewById<MaterialCardView>(R.id.card_background)
        private val cardIcon = item.findViewById<ImageView>(R.id.card_icon)
        private val cardTitle = item.findViewById<TextView>(R.id.card_title)

        fun bind(item: HomeMenuItem) {
            cardBackground.setBackgroundColor(item.color)
            cardIcon.setImageDrawable(item.drawable)
            cardTitle.text = item.title
            cardBackground.setOnClickListener {
                val fragment = item.fragmentSupplier.invoke()
                this@HomeMenuItemAdapter.clickListener.invoke(fragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_menu_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(content[position])

    override fun getItemCount(): Int = content.size
}