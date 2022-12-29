package com.hit.appnexthomeassignment.di

import android.content.Context
import com.hit.appnexthomeassignment.api.IAppNextApi
import com.hit.appnexthomeassignment.interactors.IDataRecieveInteractor
import com.hit.appnexthomeassignment.preferences.api.ISharedPreferences
import com.hit.appnexthomeassignment.repository.INetworkRepository
import com.hit.appnexthomeassignment.utils.api.IEventManager
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun getSharedPreferences(): ISharedPreferences
    fun getNetworkRepository(): INetworkRepository
    fun getRetrofit(): Retrofit
    fun getAppNextApi(): IAppNextApi
    fun getDataReceiverInteractor(): IDataRecieveInteractor
    fun getAppContext(): Context
    fun getEventManager(): IEventManager

}