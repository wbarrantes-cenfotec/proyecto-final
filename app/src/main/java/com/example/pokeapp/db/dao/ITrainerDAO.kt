package com.example.pokeapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokeapp.db.entities.Trainer

@Dao
interface ITrainerDAO {

    @Query("SELECT * FROM Trainer")
    fun getTrainer(): LiveData<List<Trainer>>

    @Insert
    suspend fun insertTrainer(trainer: Trainer)

    @Query("SELECT COUNT(trainer_name) FROM Trainer")
    fun getTotalTrainers(): LiveData<Int>

}