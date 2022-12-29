package com.hit.appnexthomeassignment.utils.impl

import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.utils.api.IEventManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventManager: IEventManager {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val weekylDataFlow = MutableStateFlow<MutableList<WeeklyData>?>(mutableListOf())

    override fun emitWeeklyData(data: MutableList<WeeklyData>?) {
        scope.launch {
            weekylDataFlow.emit(data)
        }
    }

    override fun getWeeklyData(): Flow<MutableList<WeeklyData>?> = weekylDataFlow.asStateFlow()
}