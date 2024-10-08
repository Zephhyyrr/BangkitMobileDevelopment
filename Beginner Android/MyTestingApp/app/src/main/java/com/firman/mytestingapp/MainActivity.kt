package com.firman.mytestingapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private  lateinit var btnSetValue: Button
    private  lateinit var  tvText: TextView

    private var names = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvText = findViewById(R.id.tv_text)
        btnSetValue = findViewById(R.id.btn_set_value)

        btnSetValue.setOnClickListener(this)

        names.add("Firman Ardiansyah")
        names.add("Nana")
        names.add("Rahmat")
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_set_value){
            Log.d("MainActivity", names.toString())
            val name = StringBuilder()
            for (i in 0..2){
                name.append(names[i]).append("\n")
            }
            tvText.text = name.toString()
        }
    }
}