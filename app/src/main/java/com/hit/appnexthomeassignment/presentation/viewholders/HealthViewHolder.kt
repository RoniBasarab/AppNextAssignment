package com.hit.appnexthomeassignment.presentation.viewholders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.hit.appnexthomeassignment.R
import com.hit.appnexthomeassignment.databinding.ListItemBinding
import com.hit.appnexthomeassignment.models.WeeklyData
import java.util.*
import kotlin.collections.ArrayList

class HealthViewHolder(
    private val binding: ListItemBinding,
    private val pieChart: PieChart
): RecyclerView.ViewHolder(binding.root) {

    fun bind(statistics: WeeklyData, day: String, dayInMonth: Int) {
        setData(statistics,day,dayInMonth)
    }

    private fun setData(statistics: WeeklyData, day: String, dayInMonth: Int) {
        binding.currentDate.text = dayInMonth.toString()
        binding.currentDay.text = day

        if(isToday(dayInMonth)) {
            binding.isTodayMarker.visibility = View.VISIBLE
        } else {
            binding.isTodayMarker.visibility = View.GONE
        }


        setPieChartData(statistics)

        val goal = statistics.daily_item?.daily_goal!!
        val activity = statistics.daily_item?.daily_activity!!
        val range = statistics.daily_data?.daily_distance_meters
        val kcal = statistics.daily_data?.daily_kcal

        binding.goalTv.text = goal.toString()
        binding.activityDoneTv.text = activity.toString()
        binding.kcalNumberTv.text = kcal.toString()

        if(activity >= goal) {
            binding.activityDoneTv.setTextColor(Color.rgb(0,185,63))
            binding.isachievedTv1.setImageResource(R.drawable.achieved)
            binding.isachievedTv2.setImageResource(R.drawable.achieved)
        } else {
            binding.activityDoneTv.setTextColor(Color.rgb(2,132,253))
        }

        if(range!! >= 1000) {
            binding.rangeTv.text = "KM"
            binding.rangeNumberTv.text = (range / 1000f).toString()
        } else {
            binding.rangeTv.text = "M"
            binding.rangeNumberTv.text = range.toString()
        }

    }

    private fun setPieChartData(statistics: WeeklyData) {
        pieChart.setUsePercentValues(false)
        pieChart.description.isEnabled = false
        pieChart.isDragDecelerationEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 60f
        pieChart.transparentCircleRadius = 40f
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)

        val goal = statistics.daily_item?.daily_goal!!
        val activity = statistics.daily_item?.daily_activity!!
        val entries: ArrayList<PieEntry> = ArrayList()

        if(activity >= goal) {
            entries.add(PieEntry(activity.toFloat()))
            entries.add(PieEntry(0f))
        } else {
            entries.add(PieEntry(activity.toFloat()))
            entries.add(PieEntry(goal.toFloat() - activity.toFloat()))
        }



        val dataSet = PieDataSet(entries, "Health Status")
        dataSet.setDrawIcons(false)
        dataSet.setDrawValues(false)

        dataSet.sliceSpace = 0f



        if(activity >= goal) {
            dataSet.colors = mutableListOf(
                Color.rgb(0,185,63)
            )
        } else {
            dataSet.colors = mutableListOf(
                Color.rgb(2,132,253),
                Color.rgb(239, 239, 239)
            )
        }

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        pieChart.data = data
        pieChart.invalidate()
    }


    private fun isToday(dayInMonth: Int): Boolean {
        val currentDay = (Calendar.getInstance().get(Calendar.DATE)) % 30 + 1
        return currentDay == dayInMonth
    }
}