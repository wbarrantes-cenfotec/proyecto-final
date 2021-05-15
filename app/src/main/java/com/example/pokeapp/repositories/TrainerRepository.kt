package com.example.pokeapp.repositories

import android.content.Context
import com.example.pokeapp.db.PokeAppDatabase
import com.example.pokeapp.db.entities.Trainer
import io.reactivex.Observable

class TrainerRepository(context: Context) {

    // Initialize the database
    var database = PokeAppDatabase.getDatabase(context)

    suspend fun insertTrainer(trainer: Trainer) {
        database.getTrainerDAO().insertTrainer(trainer)
    }

    fun getTotalTrainers(): Observable<Int> = database.getTrainerDAO().getTotalTrainers()
}