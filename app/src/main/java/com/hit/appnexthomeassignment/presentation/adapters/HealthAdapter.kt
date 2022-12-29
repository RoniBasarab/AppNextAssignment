package com.hit.appnexthomeassignment.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hit.appnexthomeassignment.databinding.ListItemBinding
import com.hit.appnexthomeassignment.models.CustomDateModel
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.presentation.viewholders.HealthViewHolder

class HealthAdapter(
    private val healthStats: MutableList<WeeklyData>,
    private var date: CustomDateModel
):RecyclerView.Adapter<HealthViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val pieChart = binding.pieChart
        return HealthViewHolder(binding,pieChart)
    }

    override fun onBindViewHolder(holder: HealthViewHolder, position: Int) {
        holder.bind(
            healthStats[position],
            date.day[position],
            date.dayInMonth[position]
        )
    }

    override fun getItemCount(): Int {
      return healthStats.size
    }

    fun updateItems(list: MutableList<WeeklyData>, newDate: CustomDateModel) {
        healthStats.clear()
        healthStats.addAll(list)
        date = newDate
        notifyDataSetChanged()
    }

    fun getItems(): MutableList<WeeklyData> {
        return healthStats
    }
}