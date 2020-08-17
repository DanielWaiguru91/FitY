package tech.danielwaiguru.fity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.run_item.view.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.database.Run
import tech.danielwaiguru.fity.utils.TimeUtils
import kotlin.math.round

class RunAdapter: RecyclerView.Adapter<RunViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Run>(){
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val difference = AsyncListDiffer(this, diffCallback)
    fun submitList(runs: List<Run>) = difference.submitList(runs)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.run_item, parent, false)
        )
    }

    override fun getItemCount(): Int = difference.currentList.size

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = difference.currentList[position]
       holder.itemView.apply {
           Glide.with(this).load(run.image).into(routeImage)
           val speed = "${run.averageSpeed}Km/h"
          runSpeed.text = speed
           val distance = round(((run.distance / 1000f) * 10f) / 10f)
           val distanceString = "${distance}Km"
           runDistance.text = distanceString
           val time = TimeUtils.formatTime(run.timeTaken)
           runTime.text = time
       }
    }
}