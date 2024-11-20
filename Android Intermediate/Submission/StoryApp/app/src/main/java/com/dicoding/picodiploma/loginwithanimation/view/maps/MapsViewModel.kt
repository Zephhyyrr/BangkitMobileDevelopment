package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserRepository

class MapsViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getStoriesWithLocation() = userRepository.getStoriesWithLocation()
}