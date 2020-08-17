package tech.danielwaiguru.fity.ui.views

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.Constants.IS_USER_LOGGED_IN
import tech.danielwaiguru.fity.common.Constants.NAME_KEY
import tech.danielwaiguru.fity.common.Constants.WEIGHT_KEY
import tech.danielwaiguru.fity.common.toast
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : DialogFragment() {
    @Inject
    lateinit var sharedPrefs: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    private fun initListeners(){
        saveDetails.setOnClickListener { editUserToPreferences() }
    }
    private fun editUserToPreferences(){
        val editedName = etUsername.text.toString()
        val editedWeight = etWeight.text.toString()
        if (!validateUserInputs()){
            activity?.toast(getString(R.string.empty_fields_error))
            return
        }
        sharedPrefs.edit()
            .putString(NAME_KEY, editedName)
            .putFloat(WEIGHT_KEY, editedWeight.toFloat())
            .putBoolean(IS_USER_LOGGED_IN, true)
            .apply()
        val title = "Hello, $editedName"
    }
    private fun validateUserInputs(): Boolean{
        var isValid = true
        etUsername.error = if (etUsername.text.toString().length < 5){
            isValid = false
            getString(R.string.name_error)
        } else null
        etWeight.error = if (etWeight.text.toString().isEmpty()){
            isValid = false
            getString(R.string.weight_error)
        } else null
        return isValid
    }
}