package com.hit.appnexthomeassignment.utils.impl

import android.content.Context
import androidx.work.*
import com.hit.appnexthomeassignment.services.AppNextForegroundService
import java.util.concurrent.TimeUnit

class FetchApiWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    ): CoroutineWorker(
    appContext, workerParams
) {

     private val myWork = OneTimeWorkRequestBuilder<FetchApiWorker>()
         .setInitialDelay(12, TimeUnit.HOURS)
         .build()

    override suspend fun doWork(): Result {
        AppNextForegroundService.workerFetchApiData()
        WorkManager.getInstance(appContext)
            .beginUniqueWork(
                "FetchApiWork",
                ExistingWorkPolicy.REPLACE,
                myWork
            ).enqueue()

        return Result.success()
    }

}