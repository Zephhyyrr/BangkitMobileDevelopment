package com.firman.dicodingevent.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firman.dicodingevent.R
import com.firman.dicodingevent.ui.ui.upcoming.UpcomingEventFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Pastikan Anda memiliki layout XML ini

        // Menampilkan DashboardFragment di MainActivity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UpcomingEventFragment())
                .commit()
        }
    }
}
