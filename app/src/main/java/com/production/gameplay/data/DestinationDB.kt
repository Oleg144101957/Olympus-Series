package com.production.gameplay.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.production.gameplay.model.Destination

@Database(entities = [Destination::class], exportSchema = true, version = 1)
abstract class DestinationDB : RoomDatabase(){

    abstract fun getDestinationDao(): DestinationDao

    companion object{
        @Volatile
        var INSTANCE: DestinationDB? = null

        fun getDestinationDatabase(context: Context) : DestinationDB{
            val tmp = INSTANCE
            if (tmp != null) return tmp

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DestinationDB::class.java,
                    "destination_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}