package com.hit.appnexthomeassignment.models

data class WeeklyData(
    var daily_data: DailyData? = null,
    var daily_item: DailyItem? = null
)