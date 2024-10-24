package com.firman.dicodingevent.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.firman.dicodingevent.data.entity.EventEntity

@Dao
interface FavoriteEventDao {

    @Query("SELECT * FROM event ORDER BY id ASC")
    fun getAllFavoriteEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE favorite = 1")
    fun getBookmarkedEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvents(events: List<EventEntity>)

    @Update
    fun updateEvent(event: EventEntity)

    @Query("DELETE FROM event WHERE favorite = 0")
    fun deleteAllNonFavorite()

    @Query("SELECT EXISTS(SELECT * FROM event WHERE id = :eventId AND favorite = 1)")
    fun isEventFavorite(eventId: String): Boolean
}
