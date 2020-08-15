package tech.danielwaiguru.fity.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.CredentialValidator
import javax.inject.Inject

class ProfileFragment : DialogFragment() {
    @Inject
    lateinit var credentialValidator: CredentialValidator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCredentials()
    }
    private fun saveUserToPreferences(){
        if (credentialValidator.areTheDetailsValid()){

        }
    }
    private fun setCredentials(){
        val username = etUsername.text.toString()
        val weight = etWeight.text.toString()
        credentialValidator.setDetails(username, weight.toFloat())
    }
}