package com.hit.appnexthomeassignment.presentation.views.activities.application

import android.app.Application
import com.hit.appnexthomeassignment.di.ApplicationComponent
import com.hit.appnexthomeassignment.di.ApplicationInjector

class MyApplication: Application(){

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        applicationComponent = ApplicationInjector.initComponent(this)

    }
    companion object {
        lateinit var instance: MyApplication
    }
}