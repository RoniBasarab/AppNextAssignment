package com.hit.appnexthomeassignment.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.hit.appnexthomeassignment.R
import com.hit.appnexthomeassignment.di.ApplicationInjector
import com.hit.appnexthomeassignment.interactors.IDataRecieveInteractor
import com.hit.appnexthomeassignment.preferences.api.ISharedPreferences
import com.hit.appnexthomeassignment.presentation.views.activities.MainActivity
import com.hit.appnexthomeassignment.utils.impl.FetchApiWorker
import com.hit.appnexthomeassignment.utils.api.IEventManager
import kotlinx.coroutines.*

class AppNextForegroundService: LifecycleService() {

    private val scope = lifecycleScope.plus(SupervisorJob() + Dispatchers.IO)
    private lateinit var dataRecieverInteractor: IDataRecieveInteractor
    private lateinit var preferences: ISharedPreferences
    private lateinit var eventManager: IEventManager
    private lateinit var notificationManager: NotificationManager

    private val fetchApiWorkRequest = OneTimeWorkRequestBuilder<FetchApiWorker>()
        .build()

    init {
        attachLifecycleListeners()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    private fun attachLifecycleListeners() {
        val listener = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                initDependencies()
                startNotification()
                startFetchApiWorker()
                fetchDataFromApi()
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
            }
        }
        lifecycle.addObserver(listener)
    }

    private fun initDependencies() {
        dataRecieverInteractor = ApplicationInjector.applicationComponent.getDataReceiverInteractor()
        preferences = ApplicationInjector.applicationComponent.getSharedPreferences()
        eventManager = ApplicationInjector.applicationComponent.getEventManager()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun startNotification() {
        setNotificationChannel()
        startForeground(
            1,
            getNotification()
        )
    }

    private fun getNotification(): Notification {
        val builder = NotificationCompat.Builder(this, "1")
            .setOnlyAlertOnce(true)
            .setContentTitle(
                "Appnext notification!"
            )
            .setContentIntent(getNotificationIntent())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setColor(getColor(R.color.teal_700))
        return builder.build()
    }

    private fun getNotificationIntent(): PendingIntent? = PendingIntent.getActivity(
        this,
        0,
        Intent(
            this, MainActivity::class.java
        ),
        PENDING_INTENT_FLAGS
    )


    private fun setNotificationChannel() {
        val channel = NotificationChannel(
            "1",
            "main_channel",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun fetchDataFromApi() {
        if(preferences.getApiNeedsToBeCalled()) {
            scope.launch {
                dataRecieverInteractor.getWeeklyData().collect { dataObject ->
                    if(dataObject.successObject != null) {
                        val data = dataObject.successObject
                        (0..6).forEach {
                            preferences.setWeeklyData(data!![it], it.toString())
                        }
                        preferences.setApiNeedsToBeCalled(needsToBeCalled = false)
                        eventManager.emitWeeklyData(data)
                    }
                }
            }
        } else {
            val data = preferences.getWeeklyData()
            eventManager.emitWeeklyData(data)
        }
    }

    private fun startFetchApiWorker() {
        WorkManager.getInstance(this)
            .beginUniqueWork(
                "FetchApiWork",
                ExistingWorkPolicy.REPLACE,
                fetchApiWorkRequest
            ).enqueue()
    }


    companion object {
        private const val PENDING_INTENT_FLAGS =
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        /** I couldn't inject exetrnal dependencies for the FetchApiWorker and I've had a lot of trouble with it
         * Therefore I created this hack, this is very far from ideal but just to make things work, I've done it this way.
         * I'll be glad to learn how to properly utilize this mechanism.
         * */
        fun workerFetchApiData() {
            val dataRecieverInteractor = ApplicationInjector.applicationComponent.getDataReceiverInteractor()
            val preferences = ApplicationInjector.applicationComponent.getSharedPreferences()
            val eventManager = ApplicationInjector.applicationComponent.getEventManager()
            val scope = CoroutineScope(Dispatchers.IO)

            Log.d("happy", "calling api again with worker")
            scope.launch {
                if(preferences.getApiNeedsToBeCalled()) {
                    scope.launch {
                        dataRecieverInteractor.getWeeklyData().collect { dataObject ->
                            if(dataObject.successObject != null) {
                                val data = dataObject.successObject
                                (0..6).forEach {
                                    preferences.setWeeklyData(data!![it], it.toString())
                                }
                                preferences.setApiNeedsToBeCalled(needsToBeCalled = false)
                                eventManager.emitWeeklyData(data)
                            }
                        }
                    }
                } else {
                    val data = preferences.getWeeklyData()
                    eventManager.emitWeeklyData(data)
                }
            }
        }
    }
}