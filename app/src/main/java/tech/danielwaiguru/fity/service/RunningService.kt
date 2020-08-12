package tech.danielwaiguru.fity.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.Constants.ACTION_PAUSE
import tech.danielwaiguru.fity.common.Constants.ACTION_RESUME_RUNNING_FRAGMENT
import tech.danielwaiguru.fity.common.Constants.ACTION_START
import tech.danielwaiguru.fity.common.Constants.ACTION_STOP
import tech.danielwaiguru.fity.common.Constants.NOTIFICATION_CHANNEL_ID
import tech.danielwaiguru.fity.common.Constants.NOTIFICATION_CHANNEL_NAME
import tech.danielwaiguru.fity.ui.views.MainActivity
import timber.log.Timber

class RunningService: LifecycleService(){
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action){
                ACTION_START -> {
                    Timber.d("Service started")
                }
                ACTION_PAUSE -> {
                    Timber.d("Service paused")
                }
                ACTION_STOP -> {
                    Timber.d("Service stopped")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    private fun createNotification(){
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_run)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.timer))
            .setAutoCancel(false)
            .setOngoing(true)
    }
    private fun pendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_RESUME_RUNNING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )
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