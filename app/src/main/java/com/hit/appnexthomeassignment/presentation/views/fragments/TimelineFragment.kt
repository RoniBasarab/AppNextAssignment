package com.hit.appnexthomeassignment.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hit.appnexthomeassignment.databinding.FragmentTimelineBinding
import com.hit.appnexthomeassignment.models.CustomDateModel
import com.hit.appnexthomeassignment.presentation.adapters.HealthAdapter
import com.hit.appnexthomeassignment.presentation.viewmodels.TimelineFragmentViewModel
import com.hit.appnexthomeassignment.presentation.views.activities.MainActivity
import java.util.*

class TimelineFragment : Fragment() {

    private lateinit var binding: FragmentTimelineBinding
    private var adapter: HealthAdapter = HealthAdapter(mutableListOf(), CustomDateModel(
        mutableListOf(), mutableListOf()))

    private val viewModel: TimelineFragmentViewModel by viewModels {
        TimelineFragmentViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimelineBinding.inflate(layoutInflater,container,false)
        binding.currentMonthTv.text = viewModel.convertMonthToString(Calendar.getInstance().get(Calendar.MONTH))
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this@TimelineFragment.context)

        initViewModelListeners()
        initFragmentScreenListeners()
        return binding.root
    }

    private fun initFragmentScreenListeners() {
        binding.backTv.setOnClickListener {
            (activity as MainActivity).launchFragment(
                MainScreenFragment(),
                (requireView().parent as ViewGroup).id,
                ""
            )
        }
    }

    private fun initViewModelListeners() {
        viewModel.weeklyDataLiveData.observe(viewLifecycleOwner) { data ->
            adapter.updateItems(data!!, viewModel.createCustomDateModel())
        }
    }
}