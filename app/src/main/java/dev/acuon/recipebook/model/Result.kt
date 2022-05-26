package dev.acuon.recipebook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
)