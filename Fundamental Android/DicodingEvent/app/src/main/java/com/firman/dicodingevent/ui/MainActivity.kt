package com.firman.dicodingevent.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firman.dicodingevent.R
import com.firman.dicodingevent.ui.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}
