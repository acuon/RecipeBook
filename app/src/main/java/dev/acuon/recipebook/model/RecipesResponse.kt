package dev.acuon.recipebook.model

data class RecipesResponse(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)