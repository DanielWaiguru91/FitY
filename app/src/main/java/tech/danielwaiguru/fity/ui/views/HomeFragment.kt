package tech.danielwaiguru.fity.ui.views

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.adapters.RunAdapter
import tech.danielwaiguru.fity.common.Constants.REQUEST_PERMISSIONS_CODE
import tech.danielwaiguru.fity.common.Constants.RUNTIME_PERMISSIONS
import tech.danielwaiguru.fity.ui.viewmodels.RunViewModel
import tech.danielwaiguru.fity.utils.LocationUtils
import tech.danielwaiguru.fity.utils.SortCriteria

@AndroidEntryPoint
class HomeFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private val runAdapter: RunAdapter by lazy { RunAdapter() }
    private val runViewModel: RunViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*toolbar.inflateMenu(R.menu.home_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.profile -> {
                    initProfileFragment()
                    activity?.toast("profile")
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }*/
        requestPermissions()
        setupRecyclerView()
        sortSelection()
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position){
                    0 -> runViewModel.sortRuns(SortCriteria.DATE)
                    1 -> runViewModel.sortRuns(SortCriteria.DISTANCE)
                    2 -> runViewModel.sortRuns(SortCriteria.TIME)
                    3 -> runViewModel.sortRuns(SortCriteria.SPEED)
                    4 -> runViewModel.sortRuns(SortCriteria.CALORIES)
                }
            }
        }
        runViewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })
        initListeners()
    }
    private fun initListeners(){
        fab.setOnClickListener { initRunningProgressFragment() }
    }

    private fun setupRecyclerView() = runsRecyclerView.apply {
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
    private fun sortSelection() = when (runViewModel.criteria){
        SortCriteria.DATE -> filterSpinner.setSelection(0)
        SortCriteria.DISTANCE -> filterSpinner.setSelection(1)
        SortCriteria.TIME -> filterSpinner.setSelection(2)
        SortCriteria.SPEED -> filterSpinner.setSelection(3)
        SortCriteria.CALORIES -> filterSpinner.setSelection(4)
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
    private fun initRunningProgressFragment(){
        view?.let {
            val action = HomeFragmentDirections.actionHomeFragment2ToRunProgressFragment()
            it.findNavController().navigate(action)
        }
    }
}