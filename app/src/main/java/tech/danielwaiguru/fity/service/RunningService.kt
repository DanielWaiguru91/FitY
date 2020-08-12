package tech.danielwaiguru.fity.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import tech.danielwaiguru.fity.common.Constants.ACTION_PAUSE
import tech.danielwaiguru.fity.common.Constants.ACTION_START
import tech.danielwaiguru.fity.common.Constants.ACTION_STOP
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
}