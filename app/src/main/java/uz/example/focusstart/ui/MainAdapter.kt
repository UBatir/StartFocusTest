package uz.example.focusstart.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import uz.example.focusstart.R
import uz.example.focusstart.data.model.Currency
import uz.example.focusstart.databinding.ItemCurrencyBinding
import uz.example.focusstart.utils.scope
import kotlin.math.roundToInt

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var models:List<Currency> = listOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field= value
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemCurrencyBinding::bind)

        fun populateModel(model: Currency) = binding.scope {
            tvName.text=model.Name
            tvCharCode.text=model.CharCode
            tvValue.text=((model.Value/model.Nominal*1000).roundToInt() / 1000.0).toString()
            val country=model.CharCode.dropLast(1).lowercase()
            val glideUrl = GlideUrl("https://flagcdn.com/h20/${country}.png")
            Glide.with(root)
                .load(glideUrl)
                .circleCrop()
                .into(ivFlag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size
}