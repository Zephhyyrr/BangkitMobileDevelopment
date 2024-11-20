package com.dicoding.picodiploma.loginwithanimation.view.upload

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserRepository
import java.io.File

class UpStoryViewModel(private val repository: UserRepository) : ViewModel() {
    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
    fun uploadImageWithLocation(
        imageFile: File,
        description: String,
        latitude: Double,
        longitude: Double
    ) = repository.uploadImageWithLocation(imageFile, description, latitude, longitude)
}