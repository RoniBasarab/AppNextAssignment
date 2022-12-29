package com.hit.appnexthomeassignment.di

import com.hit.appnexthomeassignment.presentation.views.activities.application.MyApplication

class ApplicationInjector {

    companion object {
        lateinit var applicationComponent: ApplicationComponent

        fun initComponent(app: MyApplication): ApplicationComponent {

            applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(app))
                .build()
            return applicationComponent
        }
    }
}