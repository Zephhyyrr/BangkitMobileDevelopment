package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.local.model.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.local.pref.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getStories(context: Context): LiveData<List<ListStoryItem>> {
        return repository.getStories(context)
    }
}