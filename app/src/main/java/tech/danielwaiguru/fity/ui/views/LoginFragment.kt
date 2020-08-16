package tech.danielwaiguru.fity.ui.views

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.Constants
import tech.danielwaiguru.fity.common.Constants.IS_USER_LOGGED_IN
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(){
    @Inject
    lateinit var sharedPrefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserLoggedInStatus()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    private fun initListeners(){
        buttonGo.setOnClickListener { writeUserToPrefs() }
    }

    private fun writeUserToPrefs(){
        val usernameText = username.text.toString()
        val weightText = weight.text.toString()
        if (usernameText.isEmpty() || weightText.isEmpty()){
            Snackbar.make(requireView(), getString(R.string.empty_fields_error), Snackbar.LENGTH_LONG).show()
            return
        }
        sharedPrefs.edit()
            .putString(Constants.NAME_KEY, usernameText)
            .putFloat(Constants.WEIGHT_KEY, weightText.toFloat())
            .putBoolean(IS_USER_LOGGED_IN, true)
            .apply()
        initHome()
    }
    private fun checkUserLoggedInStatus(){
        view?.let {
            val state = sharedPrefs.getBoolean(IS_USER_LOGGED_IN, false)
            if (state){
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostFragment, HomeFragment())
                    .addToBackStack(null)
                    .commit()

            }
        }
    }
    private fun initHome(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, HomeFragment())
            .addToBackStack(null)
            .commit()
    }
}