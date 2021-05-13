package com.example.pokeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokeapp.db.dao.ITrainerDAO
import com.example.pokeapp.db.entities.Trainer

@Database(entities = arrayOf(Trainer::class), version = 1)
abstract class PokeAppDatabase : RoomDatabase() {

    abstract fun getTrainerDAO(): ITrainerDAO

    companion object {
        @Volatile
        private var INSTANCE: PokeAppDatabase? = null

        fun getDatabase(context: Context): PokeAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokeAppDatabase::class.java,
                    "poke-app-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}