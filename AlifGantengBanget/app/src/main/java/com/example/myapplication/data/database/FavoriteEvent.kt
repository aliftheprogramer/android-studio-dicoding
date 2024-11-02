package com.example.myapplication.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "eventName")
    var eventName: String? = null,

    @ColumnInfo(name = "eventSummary")
    var eventSummary: String? = null,

    @ColumnInfo(name = "imageLogo")
    var imageLogo: String? = null,

    @ColumnInfo(name = "eventDate")
    var eventDate: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "createdAt")
    var createdAt: Long = System.currentTimeMillis()
) : Parcelable