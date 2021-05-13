package com.example.pokeapp.viewmodels

import android.app.Application
import android.database.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.db.entities.Trainer
import com.example.pokeapp.repositories.TrainerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var trainerRepository: TrainerRepository = TrainerRepository(application.applicationContext)

    fun createNewTrainer(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            trainerRepository.insertTrainer(Trainer(name))
        }
    }

    fun hasValidTrainer(): LiveData<Int> {
        return trainerRepository.getTotalTrainers()
    }
}