package tech.danielwaiguru.fity.ui.views

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_report.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.ui.viewmodels.RunReportViewModel
import tech.danielwaiguru.fity.utils.TimeUtils
import kotlin.math.round

@AndroidEntryPoint
class ReportFragment : Fragment() {
    private val runReportViewModel:RunReportViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setup()
    }
    //configure bar graph
    private fun setup(){
        runChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        runChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        runChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        runChart.apply {
            description.text = context.getString(R.string.bar_title)
            legend.isEnabled = false
        }
    }
    private fun observeData(){
        runReportViewModel.totalTime().observe(viewLifecycleOwner, Observer {time ->
            time?.let {
                val timeRan = TimeUtils.formatTime(it)
                totalTime.text = timeRan
            }
        })
        runReportViewModel.totalSpeed().observe(viewLifecycleOwner, Observer { speed ->
            speed?.let {
                val ranAverageSpeed = round(it * 10f) / 10f
                val speedString = "${ranAverageSpeed}Km/h"
                averageSpeed.text = speedString
            }
        })
        runReportViewModel.totalDistance().observe(viewLifecycleOwner, Observer { distance ->
            distance?.let {
                val distanceInKm = round(((it  / 1000f) / 10f) * 10f)
                val distanceString = "${distanceInKm}Km"
                totalDistance.text = distanceString
            }
        })
        runReportViewModel.totalCaloriesBurned().observe(viewLifecycleOwner, Observer { totalCalories ->
            totalCalories?.let {
                val calories = "${it}kcal"
                caloriesBurn.text = calories
            }
        })
        runReportViewModel.runsByDate().observe(viewLifecycleOwner, Observer { runs ->
            runs?.let {
                val averageSpeed =
                    it.indices.map { i -> BarEntry(i.toFloat(), it[i].averageSpeed) }
                val dataSet = BarDataSet(averageSpeed, "Running Graph").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }
                runChart.data = BarData(dataSet)
                runChart.invalidate()
                runChart.animateY(2000)
            }
        })
    }
}