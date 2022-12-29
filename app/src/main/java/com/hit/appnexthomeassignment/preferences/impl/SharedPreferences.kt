package com.hit.appnexthomeassignment.preferences.impl
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hit.appnexthomeassignment.di.ApplicationInjector
import com.hit.appnexthomeassignment.models.WeeklyData
import com.hit.appnexthomeassignment.preferences.api.ISharedPreferences

class SharedPreferences(
    private val gson: Gson
): ISharedPreferences {

    private val preferences: SharedPreferences =
        ApplicationInjector.applicationComponent .getAppContext().getSharedPreferences(
            PREFS_NAME_USER,
            Context.MODE_PRIVATE
        )

    override fun setWeeklyData(data: WeeklyData, day: String) {
        val editor = preferences.edit()
        editor.putString(
            KEY_DAY_DATA + day,
            gson.toJson(data)
        ).commit()
    }

    /** I didn't have enough time to implement Room to serialize a mutableList of my custom object
     * (serializing MutableList<WeeklyData> gave an error and didn't work)
     * So I just made a workaround to save each day of data in its own separate file and then pull it one by one, creating a list.
     * */

    override fun getWeeklyData(): MutableList<WeeklyData> {
        val dataList = mutableListOf<WeeklyData>()
        (0..6).forEach {
            val data = preferences.getString(KEY_DAY_DATA + it.toString(), null)
            dataList.add(gson.fromJson(data, WeeklyData::class.java))
        }
        return dataList
    }

    override fun setApiNeedsToBeCalled(needsToBeCalled: Boolean) {
        val editor = preferences.edit()

        editor.putBoolean(
            KEY_SHOULD_CALL_API,
            needsToBeCalled
        ).commit()
    }

    override fun getApiNeedsToBeCalled(): Boolean {
        return preferences.getBoolean(KEY_SHOULD_CALL_API, true)
    }

    companion object {
        const val PREFS_NAME_USER = "appnextdata"
        const val KEY_DAY_DATA = "data"
        const val KEY_SHOULD_CALL_API = "shouldcall"
    }
}