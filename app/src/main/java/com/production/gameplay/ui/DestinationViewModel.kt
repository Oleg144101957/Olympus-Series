package com.production.gameplay.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.production.gameplay.data.DestinationRepository
import com.production.gameplay.model.Destination

class DestinationViewModel(val destinationRepository: DestinationRepository) : ViewModel() {

    val liveDestination: LiveData<List<Destination>> = destinationRepository.readAllNotesFromDatabase()

    fun addDestination(destination: Destination){
        destinationRepository.addDestinationToTheDatabase(destination)
    }
}