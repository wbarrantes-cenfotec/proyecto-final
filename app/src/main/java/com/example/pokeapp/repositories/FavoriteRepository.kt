package com.example.pokeapp.repositories

import android.content.Context
import com.example.pokeapp.db.PokeAppDatabase
import com.example.pokeapp.db.entities.Favorite

class FavoriteRepository(context:Context){
    // Initialize the database
    var database = PokeAppDatabase.getDatabase(context)

    suspend fun insertFavorite (favorite: Favorite) {
        database.getFavoriteDAO().insertFavorite(favorite)
    }

}