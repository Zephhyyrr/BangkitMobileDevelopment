package com.firman.dicodingevent.ui.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.firman.dicodingevent.R
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.retrofit.ApiConfig
import com.firman.dicodingevent.database.FavoriteEventRoomDatabase
import com.firman.dicodingevent.databinding.ActivityDetailBinding
import com.firman.dicodingevent.util.AppExecutors
import java.text.SimpleDateFormat
import java.util.Locale

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

        val appExecutors = AppExecutors()

        val database = FavoriteEventRoomDatabase.getDatabase(applicationContext)
        val favoriteEventDao = database.favoriteEventDao()

        val eventRepository = EventRepository.getInstance(ApiConfig.getApiService(), favoriteEventDao, appExecutors)
        val factory = DetailViewModelFactory(eventRepository)

        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val eventId = intent.getStringExtra("EVENT_ID")

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.eventDetail.observe(this) { event ->
            if (event != null) {
                updateUI(event)
            } else {
                Log.e(TAG, "Event detail is null")
            }
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            val iconRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            binding.floatingActionButton.setImageResource(iconRes)
        }

        binding.floatingActionButton.setOnClickListener {
            val event = viewModel.eventDetail.value
            if (event != null) {
                val eventEntity = EventEntity(
                    id = event.id.toString(),
                    name = event.name,
                    mediaCover = event.mediaCover,
                    isFavorite = viewModel.isFavorite.value == true,
                    active = true
                )
                if (viewModel.isFavorite.value == true) {
                    viewModel.deleteFavorite(eventEntity)
                } else {
                    viewModel.saveFavorite(eventEntity)
                }
            }
        }

        if (eventId != null && viewModel.eventDetail.value == null) {
            viewModel.fetchEventDetail(eventId)
        } else {
            Log.e(TAG, "EVENT_ID is null or event detail already loaded")
        }
    }

    private fun updateUI(event: ListEventsItem) {
        binding.tvTitle.text = event.name
        binding.tvOrganizer.text = event.ownerName
        binding.tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvCity.text = event.cityName
        binding.tvKouta.text = (event.quota - event.registrants).toString()
        binding.btnRegister.setOnClickListener {
            val eventLink = event.link
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(eventLink)
            }
            startActivity(intent)
        }
        Glide.with(this).load(event.mediaCover).into(binding.ivDetail)

        // Setup tanggal
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // format input dari API
            val outputDateFormat = SimpleDateFormat("dd MMMM", Locale("id", "ID")) // format output (tanggal)
            val outputYearFormat = SimpleDateFormat("yyyy", Locale("id", "ID")) // format output (tahun)
            val outputTimeFormat = SimpleDateFormat("HH.mm", Locale("id", "ID")) // format output (jam)
            val startDate = inputFormat.parse(event.beginTime)
            val endDate = inputFormat.parse(event.endTime)

            val startFormatted = outputDateFormat.format(startDate!!)
            val endFormatted = outputDateFormat.format(endDate!!)
            val yearFormatted = outputYearFormat.format(endDate)
            val timeFormatted = outputTimeFormat.format(startDate)

            binding.tvEvent.text = getString(R.string.event_date_format, startFormatted, endFormatted, yearFormatted, timeFormatted)
        } catch (e: Exception) {
            binding.tvEvent.text = getString(R.string.event_date_error)
        }
    }
}
