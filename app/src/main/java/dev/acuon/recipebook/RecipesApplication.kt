package dev.acuon.recipebook

import android.app.Application
import dev.acuon.recipebook.data.local.RecipesDatabase
import dev.acuon.recipebook.data.remote.RecipesService
import dev.acuon.recipebook.data.remote.RetrofitHelper
import dev.acuon.recipebook.repo.RecipesRepository

class RecipesApplication: Application() {

    lateinit var repository: RecipesRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val recipesService = RetrofitHelper.getInstance().create(RecipesService::class.java)
        val database = RecipesDatabase.getDatabase(applicationContext)
        repository = RecipesRepository(recipesService, database, applicationContext)
    }

}