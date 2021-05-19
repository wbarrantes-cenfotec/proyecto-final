package com.example.pokeapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @ColumnInfo(name = "fav_name") val favName:String,
    @ColumnInfo(name = "url") val url:String,
    @ColumnInfo(name = "img") val img:String
    ){
    @PrimaryKey(autoGenerate = true) var identifier: Int = 0
}