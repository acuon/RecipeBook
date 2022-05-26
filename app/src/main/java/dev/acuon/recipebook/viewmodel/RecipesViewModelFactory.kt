package dev.acuon.recipebook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.acuon.recipebook.repo.RecipesRepository

class RecipesViewModelFactory(private val repository: RecipesRepository, private val type: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipesViewModel(repository, type) as T
    }
}