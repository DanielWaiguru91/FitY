package tech.danielwaiguru.fity.ui.views

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.Constants.REQUEST_PERMISSIONS_CODE
import tech.danielwaiguru.fity.common.Constants.RUNTIME_PERMISSIONS
import tech.danielwaiguru.fity.utils.LocationUtils

class HomeFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requestPermissions()
    }
    //check permissions status and request again if not already granted
    private fun requestPermissions(){
        if (LocationUtils.hasPermissions(requireContext())){
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            //android versions lower than Q
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale),
                REQUEST_PERMISSIONS_CODE,
                *RUNTIME_PERMISSIONS
            )
        }
        else{
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale),
                REQUEST_PERMISSIONS_CODE,
                *RUNTIME_PERMISSIONS,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
    //check if user has denied some permissions permanently
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
        else{
            requestPermissions()
        }
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}