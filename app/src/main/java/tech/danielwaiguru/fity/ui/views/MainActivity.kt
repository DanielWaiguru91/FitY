package tech.danielwaiguru.fity.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.gone
import tech.danielwaiguru.fity.common.visible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }
    private fun setupNavigation(){
        bottomNav.setupWithNavController(navHostFragment.findNavController())
        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment, R.id.reportFragment, R.id.reminderFragment ->{
                        bottomNav.visible()
                    }
                    else -> bottomNav.gone()
                }
            }
    }
}