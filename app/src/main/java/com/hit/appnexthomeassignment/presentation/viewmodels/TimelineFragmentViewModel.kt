package com.hit.appnexthomeassignment.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hit.appnexthomeassignment.di.ApplicationInjector
import com.hit.appnexthomeassignment.models.CustomDateModel
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.utils.api.IEventManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TimelineFragmentViewModel(
    private val eventManager: IEventManager
): ViewModel() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val weeklyDataMutableLiveData = MutableLiveData<MutableList<WeeklyData>?>()
    val weeklyDataLiveData: LiveData<MutableList<WeeklyData>?>
        get() = weeklyDataMutableLiveData

    init {
        dataListener()
        createCustomDateModel()
    }

    private fun dataListener() {
        scope.launch {
            eventManager.getWeeklyData().collect {
                weeklyDataMutableLiveData.postValue(it)
            }
        }
    }

    fun createCustomDateModel(): CustomDateModel {
        val week = mutableListOf("Sun", "Mon","Tue","Wed", "Thu", "Fri","Sat")
        val dayInMonthList = mutableListOf<Int>()
        (0..6).forEach { i ->
            dayInMonthList.add(
                i,
                ((Calendar.getInstance().get(Calendar.DATE) + i ) % 30) + 1
            )
        }
        return CustomDateModel(week, dayInMonthList)
    }

    fun convertMonthToString(month: Int): String {
        return when(month) {
            0 -> "JANUARY"
            1 -> "FEBRUARY"
            2 -> "MARCH"
            3 -> "APRIL"
            4 -> "MAY"
            5 -> "JUNE"
            6 -> "JULY"
            7 -> "AUGUST"
            8 -> "SEPTEMBER"
            9 -> "OCTOBER"
            10 -> "NOVEMBER"
            11 -> "DECEMBER"
            else -> ""
        }
    }



    companion object {
        /** created this workaround because i kept getting an error - "java.lang.UnsupportedOperationException: Factory.create(String) is unsupported This Factory requires `CreationExtras` to be passed into `create` method."
         * found solution online
         * */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TimelineFragmentViewModel(
                    ApplicationInjector.applicationComponent.getEventManager(),
                )
            }
        }
    }

}