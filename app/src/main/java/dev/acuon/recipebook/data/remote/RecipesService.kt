package dev.acuon.recipebook.data.remote

import dev.acuon.recipebook.model.RecipesResponse
import dev.acuon.recipebook.model.details.RecipeDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {

    //?type=main+course&apiKey=2456620ca8f84c19bb72c9008ca10d70
    @GET("complexSearch")
    suspend fun getRecipes(
        @Query("type") type: String,
        @Query("apiKey") apiKey: String
    ): Response<RecipesResponse>

    @GET("information")
    suspend fun getRecipeDetails(
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean
    ): Response<RecipeDetails>

}