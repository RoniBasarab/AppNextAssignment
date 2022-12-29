package com.hit.appnexthomeassignment.di

import android.content.Context
import com.google.gson.Gson
import com.hit.appnexthomeassignment.api.IAppNextApi
import com.hit.appnexthomeassignment.interactors.DataRecieveInteractor
import com.hit.appnexthomeassignment.interactors.IDataRecieveInteractor
import com.hit.appnexthomeassignment.preferences.api.ISharedPreferences
import com.hit.appnexthomeassignment.preferences.impl.SharedPreferences
import com.hit.appnexthomeassignment.repository.INetworkRepository
import com.hit.appnexthomeassignment.repository.NetworkRepository
import com.hit.appnexthomeassignment.presentation.views.activities.application.MyApplication
import com.hit.appnexthomeassignment.utils.api.IEventManager
import com.hit.appnexthomeassignment.utils.impl.EventManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val app: MyApplication
) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson
    ): Retrofit {

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://apimocha.com/nextandroid/")
            .addConverterFactory(GsonConverterFactory.create(gson))

        val httpClient = OkHttpClient.Builder()
        val okHttpClient = httpClient
            .build()

        return retrofitBuilder
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideEventManager(): IEventManager {
        return EventManager()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(gson: Gson): ISharedPreferences {
        return SharedPreferences(gson)
    }

    @Provides
    @Singleton
    fun provideDataReceiver(networkRepository: INetworkRepository): IDataRecieveInteractor {
        return DataRecieveInteractor(networkRepository)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(appNextApi: IAppNextApi): INetworkRepository {
        return NetworkRepository(appNextApi)
    }

    @Provides
    @Singleton
    fun provideSaferVpnApi(retrofit: Retrofit): IAppNextApi {
        return retrofit.create(IAppNextApi::class.java)
    }

}