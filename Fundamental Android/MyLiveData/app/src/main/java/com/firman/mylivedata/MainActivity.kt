package com.firman.mylivedata

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firman.mylivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var liveDataTimerViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        liveDataTimerViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }
    private fun subscribe(){
        val elapseTimeObserver = Observer<Long?> { along ->
            val newText = this@MainActivity.resources.getString(R.string.seconds, along)
            activityMainBinding.timerTextview.text = newText
        }
        liveDataTimerViewModel.getElapsedTime().observe(this, elapseTimeObserver)
    }
}