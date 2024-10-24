package com.firman.dicodingevent.ui.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.firman.dicodingevent.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var settingPreferences: SettingPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        settingPreferences = SettingPreferences.getInstance(requireContext().dataStore)

        switchTheme = view.findViewById(R.id.switch_theme)

        CoroutineScope(Dispatchers.Main).launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                switchTheme.isChecked = isDarkModeActive
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            settingPreferences.saveThemeSetting(isChecked)
        }

        return view
    }
}

