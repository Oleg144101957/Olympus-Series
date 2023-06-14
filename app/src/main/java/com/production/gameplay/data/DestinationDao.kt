package com.production.gameplay.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.production.gameplay.OlympusBase
import com.production.gameplay.model.Destination


@Dao
interface DestinationDao {

    @Query("SELECT * FROM ${OlympusBase.DB_NAME}")
    fun readAllDestinations(): LiveData<List<Destination>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDestination(destination: Destination)


}