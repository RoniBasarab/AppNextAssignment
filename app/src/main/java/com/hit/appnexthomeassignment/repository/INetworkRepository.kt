package com.hit.appnexthomeassignment.repository

import com.hit.appnexthomeassignment.models.BaseDomainModel
import com.hit.appnexthomeassignment.models.WeeklyData

interface INetworkRepository {
    suspend fun getData(): BaseDomainModel<MutableList<WeeklyData>>
}