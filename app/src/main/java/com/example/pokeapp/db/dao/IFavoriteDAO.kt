package com.example.pokeapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokeapp.db.entities.Favorite

@Dao
interface IFavoriteDAO{
    @Query("SELECT * FROM favorite")
    fun getAllFavorites():LiveData<List<Favorite>>

    @Insert
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE identifier=:id)")
    fun isFavorite(id: Int): Int
}