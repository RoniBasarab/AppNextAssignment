package com.hit.appnexthomeassignment.preferences.api

import com.hit.appnexthomeassignment.models.WeeklyData

interface ISharedPreferences {
    fun setWeeklyData(data:  WeeklyData, day: String)
    fun getWeeklyData(): MutableList<WeeklyData>
    fun setApiNeedsToBeCalled(needsToBeCalled: Boolean)
    fun getApiNeedsToBeCalled(): Boolean
}