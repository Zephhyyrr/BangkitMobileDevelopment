package com.firman.submissionandroidpemula

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val percentageText: TextView = findViewById(R.id.tv_percentage)
        val logoImage: View = findViewById(R.id.logo_image)

        logoImage.scaleX = 1.5f
        logoImage.scaleY = 1.5f
        logoImage.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1000)
            .start()

        ObjectAnimator.ofInt(progressBar, "progress", 0, 100).apply {
            duration = 5000
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Int
                percentageText.text = "$progress%"
            }
            start()
        }
        progressBar.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
