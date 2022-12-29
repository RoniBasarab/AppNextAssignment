package com.hit.appnexthomeassignment.utils.api
import com.hit.appnexthomeassignment.models.BaseDomainModel
import com.hit.appnexthomeassignment.models.WeeklyData
import retrofit2.Response

interface IBaseConverter<T,K> {
    fun convert(response: Response<T>): BaseDomainModel<K>
}