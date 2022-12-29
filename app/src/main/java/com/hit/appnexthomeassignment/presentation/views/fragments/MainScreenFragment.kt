package com.hit.appnexthomeassignment.presentation.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.hit.appnexthomeassignment.R
import com.hit.appnexthomeassignment.databinding.FragmentMainScreenBinding
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.presentation.viewmodels.MainScreenFragmentViewModel
import com.hit.appnexthomeassignment.presentation.views.activities.MainActivity

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var barChart: BarChart

    private val viewModel: MainScreenFragmentViewModel by viewModels {
        MainScreenFragmentViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater,container, false)
        barChart = binding.barChart

        initBarChartSettings()
        viewModel.init()
        initViewModelListeners()
        initFragmentScreenListeners()
        return binding.root
    }

    private fun initFragmentScreenListeners() {
        binding.btnTimeline.setOnClickListener {
            (activity as MainActivity).launchFragment(
                TimelineFragment(),
                (requireView().parent as ViewGroup).id,
                ""
            )
        }
    }


    private fun initViewModelListeners() {
        viewModel.weeklyDataLiveData.observe(viewLifecycleOwner) { data ->
            setBarChartData(data)
        }
    }

    private fun setBarChartData(apiData: MutableList<WeeklyData>?) {

        val barSpace = 0f
        val groupSpace = 0.4f
        val groupCount = 7
        val barWidth = 0.3f
        val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

        val yVals1: MutableList<BarEntry> = ArrayList()
        val yVals2: MutableList<BarEntry> = ArrayList()

        apiData?.forEachIndexed() { i ,day ->
            yVals1.add(BarEntry(i.toFloat(), day.daily_item!!.daily_activity!!.toFloat()))
            yVals2.add(BarEntry(i.toFloat(), day.daily_item!!.daily_goal!!.toFloat()))
        }

        val set1: BarDataSet
        val set2: BarDataSet

        if (barChart.data != null && barChart.data.dataSetCount > 0
        ) {
            set1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            set2 = barChart.data.getDataSetByIndex(1) as BarDataSet
            set1.values = yVals1
            set2.values = yVals2
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(yVals1, "Activity")
            set1.color = Color.rgb(2,132,253)
            set2 = BarDataSet(yVals2, "Daily Goal")
            set2.color = Color.rgb(0,185,63)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            dataSets.add(set2)
            val data = BarData(dataSets)
            data.setValueFormatter(LargeValueFormatter())
            data.setDrawValues(false)
            barChart.data = data
        }

        barChart.barData.barWidth = barWidth
        barChart.animateY(1400, Easing.EaseInOutQuad)

        barChart.legend.apply {
            this.isWordWrapEnabled = true
            this.textSize = 12f
            this.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            this.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            this.orientation = Legend.LegendOrientation.HORIZONTAL
            this.setDrawInside(false)
            this.form = Legend.LegendForm.CIRCLE
        }

        barChart.axisLeft.axisMinimum = 0f
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(days)
        barChart.xAxis.labelCount = days.size
        barChart.xAxis.axisMinimum = 0f
        barChart.xAxis.axisMaximum = 0 + barChart.barData.getGroupWidth(groupSpace, barSpace) * groupCount
        barChart.groupBars(0f, groupSpace, barSpace)

        barChart.invalidate()

    }

    private fun initBarChartSettings() {
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(false)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.setCenterAxisLabels(true)
        barChart.xAxis.isGranularityEnabled = true
        barChart.xAxis.granularity = 1f
        barChart.xAxis.setDrawGridLines(false)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.description.isEnabled = false
        barChart.isDoubleTapToZoomEnabled = false
    }
}