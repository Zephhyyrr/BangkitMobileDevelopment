package com.firman.mybackgroundthread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default){
                // simulate process in backhround Thread

                for(i in 0..10){
                    delay(500)
                    val percentage = i * 10
                    withContext(Dispatchers.Main){
                        //update ui in main
                        if(percentage == 100){
                            tvStatus.setText(R.string.task_completed)
                        } else{
                            tvStatus.text = String.format(getString(R.string.compressing),percentage)
                        }
                    }
                }
            }
        }
    }
}