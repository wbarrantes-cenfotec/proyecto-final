package com.example.pokeapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokeapp.db.entities.Favorite
import io.reactivex.Observable

@Dao
interface IFavoriteDAO{
    @Query("SELECT * FROM favorite")
    fun getAllFavorites():Observable<List<Favorite>>

    @Insert
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT 1 FROM favorite WHERE fav_name=:keyword")
    fun isFavorite(keyword: String): Observable<Int>

    @Query("DELETE FROM favorite WHERE fav_name=:keyword")
    fun delFavorite(keyword: String)

    @Query("SELECT COUNT(identifier) FROM favorite")
    fun getTotalFavorites(): Observable<Int>
}