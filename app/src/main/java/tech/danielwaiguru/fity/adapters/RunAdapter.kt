package tech.danielwaiguru.fity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.run_item.view.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.database.Run
import tech.danielwaiguru.fity.utils.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter: RecyclerView.Adapter<RunAdapter.RunViewHolder>() {
    class RunViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val runTime: TextView = itemView.runTime
        val runDistance: TextView = itemView.runDistance
        val runSpeed: TextView = itemView.runSpeed
        val routeImage: ImageView = itemView.routeImage
    }

    private val runsList = emptyList<Run>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.run_item, parent, false)
        )
    }

    override fun getItemCount(): Int = runsList.size

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = runsList[position]
       holder.itemView.apply {
           Glide.with(this).load(run.image).into(holder.routeImage)
           val calendar = Calendar.getInstance().apply {
               timeInMillis = run.runDate
           }
           val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
           val speed = "${run.averageSpeed}Km/h"
          holder.runSpeed.text = speed
           val distance = "${run.distance}Km"
           holder.runDistance.text = distance
           val time = TimeUtils.formatTime(run.timeTaken)
           holder.runTime.text = time
       }
    }
}