package com.hit.appnexthomeassignment.api

import com.hit.appnexthomeassignment.models.WeeklyRawData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface IAppNextApi {

    @GET("daily_data")
    suspend fun getWeeklyData(): Response<WeeklyRawData>
}