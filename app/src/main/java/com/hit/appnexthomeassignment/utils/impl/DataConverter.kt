package com.hit.appnexthomeassignment.utils.impl

import android.util.Log
import com.hit.appnexthomeassignment.models.BaseDomainModel
import com.hit.appnexthomeassignment.models.ErrorMessage
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.models.WeeklyRawData
import com.hit.appnexthomeassignment.utils.api.IBaseConverter
import retrofit2.Response

class DataConverter {

    companion object: IBaseConverter<WeeklyRawData, MutableList<WeeklyData>> {
        override fun convert(response: Response<WeeklyRawData>): BaseDomainModel<MutableList<WeeklyData>> {
            val weeklyDataObject = BaseDomainModel<MutableList<WeeklyData>>()
            if (response.isSuccessful) {
                weeklyDataObject.successObject = response.body()?.weekly_data
            } else {
                val errorString = response.errorBody()?.string()
                weeklyDataObject.errorObject = ErrorMessage(errorString)
            }
            return weeklyDataObject
        }

    }
}