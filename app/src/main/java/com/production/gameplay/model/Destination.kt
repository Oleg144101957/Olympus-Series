package com.production.gameplay.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.production.gameplay.OlympusBase
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = OlympusBase.DB_NAME)
data class Destination (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "link")
    val link: String
    ) : Parcelable