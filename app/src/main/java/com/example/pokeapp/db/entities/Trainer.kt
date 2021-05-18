package com.example.pokeapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trainer (
    @ColumnInfo val name: String,
    @ColumnInfo val email: String,
    @ColumnInfo val gender: String
) {
    @PrimaryKey(autoGenerate = true) var identifier: Int = 0
}
