package com.firman.barvolume

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editWidth: EditText
    private lateinit var editHeight: EditText
    private lateinit var editLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    companion object {
        private const val STATE_RESULT = "state_result"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editWidth = findViewById(R.id.edt_width)
        editHeight = findViewById(R.id.edt_height)
        editLength = findViewById(R.id.edt_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
        btnCalculate.setOnClickListener(this)

        if(savedInstanceState != null){
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT,tvResult.text.toString())
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_calculate){
            val inputLength = editLength.text.toString().trim()
            val inputWidth = editWidth.text.toString().trim()
            val inputHeight = editHeight.text.toString().trim()
            var  isEmptyFields = false
            if (inputLength.isEmpty()){
                isEmptyFields = true
                editLength.error = "Field ini tidak boleh kosong"
            }

            if (inputWidth.isEmpty()){
                isEmptyFields = true
                editWidth.error = "Field ini tidak boleh kosong"
            }

            if (inputHeight.isEmpty()){
                isEmptyFields = true
                editHeight.error = "Field ini tidak boleh kosong"
            }

            if(!isEmptyFields){
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                tvResult.text = volume.toString()
            }
        }
    }
}