package com.firman.dicodingevent.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "event")
@Parcelize
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "id")
    var id: String = "",

    @field:ColumnInfo(name = "name")
    var name: String = "",

    @field:ColumnInfo(name = "media_cover")
    var mediaCover: String? = null,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean
) : Parcelable