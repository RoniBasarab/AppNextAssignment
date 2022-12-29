package com.hit.appnexthomeassignment.presentation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hit.appnexthomeassignment.di.ApplicationInjector
import com.hit.appnexthomeassignment.interactors.IDataRecieveInteractor
import com.hit.appnexthomeassignment.models.DailyData
import com.hit.appnexthomeassignment.models.DailyItem
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.preferences.api.ISharedPreferences
import com.hit.appnexthomeassignment.utils.api.IEventManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainScreenFragmentViewModel(
    private val eventManager: IEventManager
): ViewModel() {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val weeklyDataMutableLiveData = MutableLiveData<MutableList<WeeklyData>?>()
    val weeklyDataLiveData: LiveData<MutableList<WeeklyData>?>
        get() = weeklyDataMutableLiveData

    fun init() {
        scope.launch {
            eventManager.getWeeklyData().collect { data ->
                weeklyDataMutableLiveData.postValue(data)
            }
        }
    }

    companion object {

        /** created this workaround because i kept getting an error - "java.lang.UnsupportedOperationException: Factory.create(String) is unsupported This Factory requires `CreationExtras` to be passed into `create` method."
         * found solution online
         * */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainScreenFragmentViewModel(
                    ApplicationInjector.applicationComponent.getEventManager()
                )
            }
        }
    }
}