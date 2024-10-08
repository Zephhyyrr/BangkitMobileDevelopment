package com.firman.submissionandroidpemula

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CHARACTER = "key_character"
    }

    private lateinit var backgroundDetail: ImageView
    private lateinit var imageDetail: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvElement: TextView
    private lateinit var sahabat1: ImageView
    private lateinit var sahabat2: ImageView
    private lateinit var sahabat3: ImageView
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initializeViews()

        character = intent.getParcelableExtra(EXTRA_CHARACTER) ?: run {
            Log.e("DetailActivity", "Character data is missing.")
            finish() // Close the activity if character data is missing
            return
        }

        displayCharacterDetails()

        val backgroundImages = resources.obtainTypedArray(R.array.data_background)
        setupBackgroundImage(backgroundImages, character.backgroundIndex)

        setupSahabatImages(character.friendIndices)

        setupToolbar()
    }

    private fun initializeViews() {
        backgroundDetail = findViewById(R.id.background_detail)
        imageDetail = findViewById(R.id.image_detail)
        tvName = findViewById(R.id.tv_name)
        tvDescription = findViewById(R.id.tv_description)
        tvElement = findViewById(R.id.tv_nama_element)
        sahabat1 = findViewById(R.id.sahabat1)
        sahabat2 = findViewById(R.id.sahabat2)
        sahabat3 = findViewById(R.id.sahabat3)
    }

    private fun displayCharacterDetails() {
        tvName.text = character.name
        tvDescription.text = character.description
        tvElement.text = character.element
        imageDetail.setImageResource(character.photo)
    }

    private fun setupSahabatImages(friendIndices: IntArray) {
        val photoResources = resources.obtainTypedArray(R.array.data_photo)

        // Check if we have enough friends to display
        if (friendIndices.size >= 3) {
            sahabat1.setImageResource(photoResources.getResourceId(friendIndices[0], -1))
            sahabat2.setImageResource(photoResources.getResourceId(friendIndices[1], -1))
            sahabat3.setImageResource(photoResources.getResourceId(friendIndices[2], -1))
        } else {
            Log.e("DetailActivity", "Not enough friends to display.")
        }
        photoResources.recycle()
    }

    private fun setupBackgroundImage(backgroundImages: TypedArray, selectedBackgroundIndex: Int) {
        if (selectedBackgroundIndex in 0 until backgroundImages.length()) {
            val backgroundResourceId = backgroundImages.getResourceId(selectedBackgroundIndex, -1)
            if (backgroundResourceId != -1) {
                backgroundDetail.setImageResource(backgroundResourceId)
            } else {
                Log.e("DetailActivity", "Background resource ID is invalid.")
            }
        } else {
            Log.e("DetailActivity", "Invalid background index: $selectedBackgroundIndex")
        }
        backgroundImages.recycle()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareCharacter(character)
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareCharacter(character: Character) {
        val shareText = "Check out this character!\nName: ${character.name}\nDescription: ${character.description}"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share character info via"))
    }
}
