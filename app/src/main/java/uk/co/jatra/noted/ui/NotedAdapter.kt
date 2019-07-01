package uk.co.jatra.noted.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class NotedAdapter<T, VH: NotedViewHolder<T>>
constructor(
    @LayoutRes val itemLayout: Int,
    val viewHolderCreator: (view: View) -> VH
) : RecyclerView.Adapter<VH>(){
    private lateinit var data: List<T>

    fun setData(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return viewHolderCreator(view)
    }

    override fun getItemCount(): Int {
        return if (!::data.isInitialized) 0
        else data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

}

abstract class NotedViewHolder<T>(itemView: View)  : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}