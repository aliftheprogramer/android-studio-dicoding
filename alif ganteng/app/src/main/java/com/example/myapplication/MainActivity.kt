package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.settings.SettingPreferences
import com.example.myapplication.ui.settings.SettingViewModelFactory
import com.example.myapplication.ui.settings.Settings
import com.example.myapplication.ui.settings.SettingsViewModel
import com.example.myapplication.ui.settings.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity (

) : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.viewModelScope.launch {
            viewModel.setThemeSettings()
        }



        val navView: BottomNavigationView = binding.navView

        // Find the NavController from the NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Define the top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_coming_soon_event, R.id.navigation_completed_event
            )
        )

        // Set up the ActionBar with the NavController and AppBarConfiguration
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up the BottomNavigationView with the NavController
        navView.setupWithNavController(navController)


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}