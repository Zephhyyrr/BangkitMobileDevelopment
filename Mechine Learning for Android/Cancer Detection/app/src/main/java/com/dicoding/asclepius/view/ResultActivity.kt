package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.database.PredictionHistory
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.history.PredictionHistoryViewModel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var predictionHistoryViewModel: PredictionHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predictionHistoryViewModel = ViewModelProvider(this).get(PredictionHistoryViewModel::class.java)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        val label = intent.getStringExtra(EXTRA_RESULT_LABEL)
        val score = intent.getStringExtra(EXTRA_RESULT_SCORE)
        binding.resultText.text = "$label"
        binding.resultScore.text = "$score"

        binding.saveButton.setOnClickListener {
            savePredictionHistory(imageUri, label, score)
        }
    }

    private fun savePredictionHistory(imageUri: Uri?, label: String?, score: String?) {
        val history = PredictionHistory(
            imageUri = imageUri.toString(),
            resultLabel = label ?: "",
            confidenceScore = score ?: ""
        )

        predictionHistoryViewModel.addPredictionHistory(history)

        Toast.makeText(this@ResultActivity, "Data saved successfully!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT_LABEL = "extra_result_label"
        const val EXTRA_RESULT_SCORE = "extra_result_score"
    }
}
