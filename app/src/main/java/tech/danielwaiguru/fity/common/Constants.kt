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
}