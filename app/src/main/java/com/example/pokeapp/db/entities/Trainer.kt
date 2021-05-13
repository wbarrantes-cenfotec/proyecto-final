package com.example.pokeapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trainer (
    @ColumnInfo(name = "trainer_name") val trainerName: String
) {
    @PrimaryKey(autoGenerate = true) var identifier: Int = 0
}
