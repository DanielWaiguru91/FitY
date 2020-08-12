package tech.danielwaiguru.fity.common

import android.Manifest

object Constants {
    val RUNTIME_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    const val REQUEST_PERMISSIONS_CODE = 1
    const val ACTION_START = "ACTION_START"
    const val ACTION_STOP = "ACTION_STOP"
    const val ACTION_PAUSE = "ACTION_PAUSE"
    const val NOTIFICATION_ID = 1
    const val NOTIFICATION_CHANNEL_ID = "channel_id"
    const val NOTIFICATION_CHANNEL_NAME = "channel_name"
    const val ACTION_RESUME_RUNNING_FRAGMENT = "RESUME"
}