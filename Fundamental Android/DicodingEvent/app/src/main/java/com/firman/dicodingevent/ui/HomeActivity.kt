package com.firman.dicodingevent.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firman.dicodingevent.R
import com.firman.dicodingevent.databinding.ActivityHomeBinding
import com.firman.dicodingevent.ui.ui.home.HomeViewModel
import com.firman.dicodingevent.ui.ui.home.HomeViewModelFactory
import com.firman.dicodingevent.ui.ui.setting.SettingPreferences
import com.firman.dicodingevent.ui.ui.setting.dataStore
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var settingPreferences: SettingPreferences
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory.getInstance(this) } // Using 'by viewModels' for better lifecycle handling

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        binding.root.post {
            val navController = findNavController(R.id.nav_host_fragment_activity_home)

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_upcoming,
                    R.id.navigation_finished,
                    R.id.navigation_setting,
                    R.id.navigation_favorite
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }

        settingPreferences = SettingPreferences.getInstance(dataStore)
        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        homeViewModel.upcomingEvents.observe(this) { events ->
        }

        homeViewModel.finishedEvents.observe(this) { events ->
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
