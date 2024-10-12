package com.firman.myviewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firman.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var activityMainBinding: ActivityMainBinding
//    private  lateinit var viewModel: MainViewModel
    private  val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        displayResult()

        activityMainBinding.btnCalculate.setOnClickListener {
            val width = activityMainBinding.edtWidth.text.toString()
            val height = activityMainBinding.edtHeight.text.toString()
            val length = activityMainBinding.edtLength.text.toString()

            when{
                width.isEmpty() ->{
                    activityMainBinding.edtWidth.error = "Masih Kosing"
                }
                height.isEmpty() ->{
                    activityMainBinding.edtHeight.error = "Masih Kosing"
                }
                length.isEmpty() ->{
                    activityMainBinding.edtLength.error = "Masih Kosing"
                }
                else -> {
                    viewModel.calculate(width, height, length)
                    displayResult()
                }
            }
        }
    }
    private fun displayResult(){
        activityMainBinding.tvResult.text = viewModel.result.toString()
    }
}