package com.hit.appnexthomeassignment.interactors

import com.hit.appnexthomeassignment.models.BaseDomainModel
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.repository.INetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DataRecieveInteractor(
    private val networkRepository: INetworkRepository
): IDataRecieveInteractor {

    override fun getWeeklyData(): Flow<BaseDomainModel<MutableList<WeeklyData>>> {
        return flow {
            emit(networkRepository.getData())
        }
    }
}