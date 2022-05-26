package dev.acuon.recipebook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipeDetails")
data class Details (
    @PrimaryKey
    val name: String,
    val image: String,
    val summary: String,
    val instructions: String
)