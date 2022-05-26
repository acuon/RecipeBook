package dev.acuon.recipebook.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.acuon.recipebook.model.Details
import dev.acuon.recipebook.model.Result

@Dao
interface RecipesDao {

    @Insert
    suspend fun addRecipes(recipes: List<Result>)

    @Query("SELECT * FROM recipes")
    suspend fun getRecipes(): List<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavoriteRecipes(details: Details)

    @Query("SELECT * FROM recipeDetails")
    suspend fun getFavoriteRecipes(): List<Details>

}