package tech.danielwaiguru.fity.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.Constants.ACTION_PAUSE
import tech.danielwaiguru.fity.common.Constants.ACTION_START
import tech.danielwaiguru.fity.common.Constants.ACTION_STOP
import tech.danielwaiguru.fity.common.Constants.FAST_UPDATE
import tech.danielwaiguru.fity.common.Constants.NOTIFICATION_CHANNEL_ID
import tech.danielwaiguru.fity.common.Constants.NOTIFICATION_CHANNEL_NAME
import tech.danielwaiguru.fity.common.Constants.NOTIFICATION_ID
import tech.danielwaiguru.fity.common.Constants.UPDATE_INTERVAL
import tech.danielwaiguru.fity.utils.LocationUtils
import tech.danielwaiguru.fity.utils.TimeUtils
import timber.log.Timber
import javax.inject.Inject

typealias route = MutableList<LatLng>
typealias routes = MutableList<route>
@AndroidEntryPoint
class RunningService: LifecycleService(){
    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient
    @Inject
    lateinit var baseNotificationBuilder : NotificationCompat.Builder

    private var isStarting = true
    private val runningTime = MutableLiveData<Long>()
    private var isTimerEnabled = false
    private var lapTime = 0L
    private var timeRan = 0L
    private var timeStarted = 0L
    private var lastSecondTimestamp = 0L
    private lateinit var currentNotification: NotificationCompat.Builder
    companion object {
        val isRunning = MutableLiveData<Boolean>()
        val routeCoords = MutableLiveData<routes>()
        val timeRanInMillis = MutableLiveData<Long>()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action){
                ACTION_START -> {
                    if (isStarting){
                        createNotification()
                        isStarting = false
                        Timber.d("Service started")
                    }
                    else{
                        startTimer()
                        Timber.d("Service resumed")
                    }
                }
                ACTION_PAUSE -> {
                    pauseService()
                    Timber.d("Service paused")
                }
                ACTION_STOP -> {
                    Timber.d("Service stopped")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        initializeRoutes()
        currentNotification = baseNotificationBuilder
        isRunning.observe(this, Observer {
            updateUserLocation(it)
            updateNotification(it)
        })
    }
    private fun pauseService(){
        isRunning.postValue(false)
        isTimerEnabled = false
    }
    private fun startTimer(){
        addEmptyRoute()
        isRunning.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isRunning.value!!){
                lapTime = System.currentTimeMillis() - timeStarted
                timeRanInMillis.postValue(timeRan + lapTime)
                if (timeRanInMillis.value!! >= lastSecondTimestamp + 1000L){
                    runningTime.postValue(runningTime.value?.plus(1))
                    lastSecondTimestamp += 100L
                }
                delay(50L)
            }
            timeRan += lapTime
        }
    }
    //update user location record
    @SuppressLint("MissingPermission")
    private fun updateUserLocation(isUserRunning: Boolean) {
        if (isUserRunning){
            if (LocationUtils.hasPermissions(this)){
                val locationRequest = LocationRequest().apply {
                    interval = UPDATE_INTERVAL
                    fastestInterval = FAST_UPDATE
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }
        else {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            if (isRunning.value!!){
                locationResult?.locations?.let { locations ->
                    for (location in locations){
                        addRoutePoint(location)
                        Timber.d("location ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }
    //initialize routes records
    private fun initializeRoutes(){
        isRunning.postValue(false)
        routeCoords.postValue(mutableListOf())
        runningTime.postValue(0L)
        timeRanInMillis.postValue(0L)
    }
    //save progress
    private fun addRoutePoint(location: Location?){
        location?.let {
            val position = LatLng(it.latitude, it.longitude)
            routeCoords.value?.apply {
                last().add(position)
                routeCoords.postValue(this)
            }
        }
    }
    private fun addEmptyRoute() = routeCoords.value?.apply {
        add(mutableListOf())
        routeCoords.postValue(this)
    } ?: routeCoords.postValue(mutableListOf(mutableListOf()))
    private fun createNotification(){
        startTimer()
        isRunning.postValue(true)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())
        runningTime.observe(this, Observer {
            val notification = currentNotification
                .setContentText(TimeUtils.formatTime(it * 1000L))
            notificationManager.notify(NOTIFICATION_ID, notification.build())
        })
    }
    //update notification as timer runs
    private fun updateNotification(isUserRunning: Boolean){
        val notificationText = if (isUserRunning) "Pause" else "Resume"
        val pendingIntent = if (isUserRunning){
            val pauseIntent = Intent(this, RunningService::class.java).apply {
                action = ACTION_PAUSE
            }
            PendingIntent.getService(this, 1, pauseIntent, FLAG_UPDATE_CURRENT)
        }
        else {
            val resumeIntent = Intent(this, RunningService::class.java).apply {
                action = ACTION_START
            }
            PendingIntent.getService(this, 2, resumeIntent, FLAG_UPDATE_CURRENT)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //remove action
        currentNotification.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(currentNotification, ArrayList<NotificationCompat.Action>())
        }
        //replace with a new action
        currentNotification = baseNotificationBuilder
            .addAction(R.drawable.ic_baseline_run, notificationText, pendingIntent)
        notificationManager.notify(NOTIFICATION_ID, currentNotification.build())
    }
    private fun createNotificationChannel(notificationManager: NotificationManager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}