package com.example.pokeapp.repositories

import android.content.Context
import com.example.pokeapp.db.PokeAppDatabase
import com.example.pokeapp.db.entities.Favorite
import io.reactivex.Observable

class FavoriteRepository(context:Context){
    // Initialize the database
    var database = PokeAppDatabase.getDatabase(context)

    suspend fun insertFavorite (favorite: Favorite) {
        database.getFavoriteDAO().insertFavorite(favorite)
    }

    fun isFavorite (keyword: String): Observable<Int> {
        return database.getFavoriteDAO().isFavorite(keyword)
    }

    fun getFavorites (): Observable<List<Favorite>> {
        return database.getFavoriteDAO().getAllFavorites()
    }

    suspend fun delFavorite (keyword: String){
        return database.getFavoriteDAO().delFavorite(keyword)
    }

    fun getTotalFavorites(): Observable<Int> = database.getFavoriteDAO().getTotalFavorites()
}