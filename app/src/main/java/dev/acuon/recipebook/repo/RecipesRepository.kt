package dev.acuon.recipebook.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.acuon.recipebook.data.local.RecipesDatabase
import dev.acuon.recipebook.data.remote.RecipesService
import dev.acuon.recipebook.model.Details
import dev.acuon.recipebook.model.RecipesResponse
import dev.acuon.recipebook.model.Result
import dev.acuon.recipebook.model.details.RecipeDetails
import dev.acuon.recipebook.utils.NetworkUtils

class RecipesRepository(
    private val recipesService: RecipesService,
    private val recipesDatabase: RecipesDatabase,
    private val applicationContext: Context
) {

    private val recipeLiveData = MutableLiveData<List<Result>>()
    private val recipeDetailsLiveData = MutableLiveData<RecipeDetails>()
    private var favoriteRecipesLiveData = MutableLiveData<List<Details>>()

    val recipeDetails: LiveData<RecipeDetails>
        get() = recipeDetailsLiveData

    val recipes: LiveData<List<Result>>
        get() = recipeLiveData

    val favoriteRecipes: LiveData<List<Details>>
        get() = favoriteRecipesLiveData

    suspend fun getRecipes(type: String, apiKey: String) {
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val result = recipesService.getRecipes(type, apiKey)
            if (result?.body() != null) {
                recipesDatabase.clearAllTables()
                recipesDatabase.recipesDao().addRecipes(result.body()!!.results)
                recipeLiveData.postValue(result.body()!!.results)
            }
        } else {
            recipeLiveData.postValue(recipesDatabase.recipesDao().getRecipes())
        }
    }

    suspend fun getRecipeDetails(apiKey: String, includeNutrition: Boolean) {
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val result = recipesService.getRecipeDetails(apiKey, includeNutrition)
            if (result?.body() != null) {
                recipeDetailsLiveData.postValue(result.body())
            }
        }
    }

    suspend fun saveToFavorites(apiKey: String, includeNutrition: Boolean) {
        val result = recipesService.getRecipeDetails(apiKey, includeNutrition)
        if (result?.body() != null) {
            val recipe = result.body()
            val details = Details(
                recipe!!.title,
                recipe.image,
                recipe.summary,
                recipe.instructions
            )
//                    recipe.analyzedInstructions[0].steps,
//                    recipe.extendedIngredients
//                )
            recipesDatabase.recipesDao().upsertFavoriteRecipes(details)
        }
    }

    suspend fun getFavoriteRecipes() {
        favoriteRecipesLiveData.postValue(recipesDatabase.recipesDao().getFavoriteRecipes())
    }
}