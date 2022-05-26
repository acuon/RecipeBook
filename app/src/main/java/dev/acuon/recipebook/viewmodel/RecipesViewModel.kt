package dev.acuon.recipebook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.acuon.recipebook.model.Details
import dev.acuon.recipebook.model.RecipesResponse
import dev.acuon.recipebook.model.Result
import dev.acuon.recipebook.model.details.RecipeDetails
import dev.acuon.recipebook.repo.RecipesRepository
import dev.acuon.recipebook.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesViewModel(private val repository: RecipesRepository, private val type: Int) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipes(Constants.recipeType[type], Constants.API_KEY)
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipeDetails(Constants.API_KEY, false)
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.saveToFavorites(Constants.API_KEY, false)
//        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteRecipes()
        }
    }

    fun saveToFavorites() = viewModelScope.launch {
        repository.saveToFavorites(Constants.API_KEY, false)
    }

    val recipes: LiveData<List<Result>>
        get() = repository.recipes

    val recipeDetails: LiveData<RecipeDetails>
        get() = repository.recipeDetails

    val favoriteRecipes: LiveData<List<Details>>
        get() = repository.favoriteRecipes
}