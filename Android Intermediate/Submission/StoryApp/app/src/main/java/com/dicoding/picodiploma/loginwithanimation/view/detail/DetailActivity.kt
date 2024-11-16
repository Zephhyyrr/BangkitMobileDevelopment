package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.loginwithanimation.utils.Result
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupAction()
    }

    private fun setupAction() {
        val userId = intent.getStringExtra(USER_ID)

        if (userId != null) {
            viewModel.getDetailStory(userId).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                    }

                    is Result.Success -> {
                        binding.tvDetailName.text = result.data.story.name
                        binding.tvDetailDescription.text = result.data.story.description
                        Glide.with(this)
                            .load(result.data.story.photoUrl)
                            .into(binding.ivDetailPhoto)
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            application,
                            "Error: ${result.error}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    companion object {
        const val USER_ID = "user id"
    }
}