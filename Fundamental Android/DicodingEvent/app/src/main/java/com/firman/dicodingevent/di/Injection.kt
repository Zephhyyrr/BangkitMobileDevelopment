package com.firman.dicodingevent.di

import android.content.Context
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.retrofit.ApiConfig
import com.firman.dicodingevent.database.FavoriteEventRoomDatabase
import com.firman.dicodingevent.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteEventRoomDatabase.getDatabase(context)
        val dao = database.favoriteEventDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}