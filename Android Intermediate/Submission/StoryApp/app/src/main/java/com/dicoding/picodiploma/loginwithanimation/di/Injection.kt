package com.dicoding.picodiploma.loginwithanimation.di

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.data.remote.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userPreferences = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreferences.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, userPreferences)
    }
}
