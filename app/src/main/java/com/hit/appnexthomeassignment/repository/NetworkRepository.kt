package com.hit.appnexthomeassignment.repository

import android.util.Log
import com.hit.appnexthomeassignment.api.IAppNextApi
import com.hit.appnexthomeassignment.models.BaseDomainModel
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.utils.impl.DataConverter
import retrofit2.Call

class NetworkRepository(
    private val appNextApi: IAppNextApi
): INetworkRepository {
    override suspend fun getData(): BaseDomainModel<MutableList<WeeklyData>> {
        return DataConverter.convert(
            appNextApi.getWeeklyData()
        )
    }
}