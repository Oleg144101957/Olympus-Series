package com.production.gameplay.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.production.gameplay.model.Destination

class DestinationRepository(val destinationDao: DestinationDao) {

    fun readAllNotesFromDatabase(): LiveData<List<Destination>>{
        return destinationDao.readAllDestinations()
    }

    fun addDestinationToTheDatabase(destination: Destination){
        Log.d("123123", "addDestinationToTheDatabase method the destination is ${destination}")
        destinationDao.addDestination(destination = destination)
    }

}