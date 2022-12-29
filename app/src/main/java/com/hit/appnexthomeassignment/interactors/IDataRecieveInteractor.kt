package com.hit.appnexthomeassignment.interactors

import com.hit.appnexthomeassignment.models.BaseDomainModel
import com.hit.appnexthomeassignment.models.WeeklyData
import kotlinx.coroutines.flow.Flow

interface IDataRecieveInteractor {
    fun getWeeklyData(): Flow<BaseDomainModel<MutableList<WeeklyData>>>
}