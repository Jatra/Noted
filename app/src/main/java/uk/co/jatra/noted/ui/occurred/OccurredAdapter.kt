package uk.co.jatra.noted.ui.occurred

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.occurrence.view.*
import uk.co.jatra.noted.R
import uk.co.jatra.noted.network.Occurrence

class OccurredAdapter : RecyclerView.Adapter<OccurredViewHolder>() {

    private lateinit var occurrences: List<Occurrence>

    //TODO add ability to modify rather than replace.
    fun setData(occurrences: List<Occurrence>) {
        this.occurrences = occurrences
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccurredViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.occurrence, parent, false)
        return OccurredViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (!::occurrences.isInitialized) 0
        else occurrences.size
    }

    override fun onBindViewHolder(holder: OccurredViewHolder, position: Int) {
        holder.bind(occurrences[position])
    }
}

class OccurredViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(occurrence: Occurrence) {
        with(itemView) {
            whoView.text = occurrence.user
            whenView.text = occurrence.time
            idView.text = occurrence.id
            dataView.text = occurrence.what
        }
    }

}
