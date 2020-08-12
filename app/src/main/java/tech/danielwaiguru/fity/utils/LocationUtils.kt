package tech.danielwaiguru.fity.utils

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import tech.danielwaiguru.fity.common.Constants.RUNTIME_PERMISSIONS

object LocationUtils {
    fun hasPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                *RUNTIME_PERMISSIONS
            )
        }
        else {
            EasyPermissions.hasPermissions(
                context,
                *RUNTIME_PERMISSIONS,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        return true
    }
}