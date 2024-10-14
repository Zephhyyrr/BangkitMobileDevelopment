package com.firman.dicodingevent.ui.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.retrofit.ApiConfig
import com.firman.dicodingevent.databinding.ActivityDetailBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val eventId = intent.getStringExtra("EVENT_ID")
        Log.d("DetailActivity", "Received EVENT_ID: $eventId")
        if (eventId != null) {
            viewModel.fetchEventDetail(eventId)
        } else {
            Log.e("DetailActivity", "EVENT_ID is null")
        }
    }


    private fun getDetailEvent(eventId: String) {
        val apiService = ApiConfig.getApiService()
        apiService.getDetailEvent(eventId).enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(call: Call<DicodingResponse>, response: Response<DicodingResponse>) {
                if (response.isSuccessful) {
                    val dicodingResponse = response.body()
                    Log.d(TAG, "Response: ${Gson().toJson(dicodingResponse)}") // Log seluruh respons

                    // Pastikan dicodingResponse tidak null dan tidak ada error
                    if (dicodingResponse != null && !dicodingResponse.error) {
                        val events = dicodingResponse.listEvents
                        // Cek apakah events tidak null dan tidak kosong
                        if (!events.isNullOrEmpty()) {
                            val event = events.firstOrNull { it.id.toString() == eventId }
                            if (event != null) {
                                // Populasi UI dengan data event
                                binding.tvEvent.text = event.name
                                binding.tvCity.text = event.cityName
                                binding.tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                                Glide.with(this@DetailActivity).load(event.mediaCover).into(binding.ivDetail)
                            } else {
                                Log.e(TAG, "Event with ID $eventId not found in listEvents")
                            }
                        } else {
                            Log.e(TAG, "listEvents is null or empty")
                        }
                    } else {
                        Log.e(TAG, "Error in response or dicodingResponse is null")
                    }
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                Log.e(TAG, "Failure: ${t.message}")
            }
        })
    }



}

