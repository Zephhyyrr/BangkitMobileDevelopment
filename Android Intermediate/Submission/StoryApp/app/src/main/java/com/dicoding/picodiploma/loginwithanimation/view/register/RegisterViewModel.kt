package com.dicoding.picodiploma.loginwithanimation.view.register

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun registerUser(name: String, email: String, password: String) = userRepository.registerUser(name, email, password)
}