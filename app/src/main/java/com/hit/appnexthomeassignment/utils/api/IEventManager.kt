package com.hit.appnexthomeassignment.utils.api

import com.hit.appnexthomeassignment.models.WeeklyData
import kotlinx.coroutines.flow.Flow

interface IEventManager {
    fun emitWeeklyData(data: MutableList<WeeklyData>?)
    fun getWeeklyData(): Flow<MutableList<WeeklyData>?>
}