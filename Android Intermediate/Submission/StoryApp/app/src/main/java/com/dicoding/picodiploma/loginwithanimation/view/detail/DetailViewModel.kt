package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDetailStory(id: String) = userRepository.getDetailStory(id)
}