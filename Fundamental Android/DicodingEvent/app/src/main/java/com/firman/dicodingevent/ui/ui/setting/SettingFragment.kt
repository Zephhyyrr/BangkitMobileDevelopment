package com.firman.dicodingevent.ui.ui.setting

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.firman.dicodingevent.R
import com.firman.dicodingevent.worker.DailyReminderWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SettingFragment : Fragment() {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var switchNotification: SwitchMaterial
    private lateinit var settingPreferences: SettingPreferences

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Aktifkan Notifikasi Harian ? ")
                    .setMessage("Izin Notifikasi Diperlukan")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        startNotificationWork()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                Toast.makeText(requireContext(), "Notifications permission rejected", Toast.LENGTH_SHORT).show()
                switchNotification.isChecked = false
            }
        }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        settingPreferences = SettingPreferences.getInstance(requireContext().dataStore)

        switchTheme = view.findViewById(R.id.switch_theme)
        switchNotification = view.findViewById(R.id.switch_notification)

        CoroutineScope(Dispatchers.Main).launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                switchTheme.isChecked = isDarkModeActive
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            settingPreferences.getNotificationSetting().collect { isNotificationActive ->
                switchNotification.isChecked = isNotificationActive
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            settingPreferences.saveThemeSetting(isChecked)
        }

        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestNotificationPermission()
            } else {
                cancelNotificationWork()
            }
            settingPreferences.saveNotificationSetting(isChecked)
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun startNotificationWork() {
        context?.let {
            val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS)
                .addTag("NotificationWorker")
                .build()
            WorkManager.getInstance(it).enqueue(workRequest)
        }
    }

    private fun cancelNotificationWork() {
        context?.let {
            WorkManager.getInstance(it).cancelAllWorkByTag("NotificationWorker")
        }
    }
}
