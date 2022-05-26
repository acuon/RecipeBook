package dev.acuon.recipebook.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dev.acuon.recipebook.R
import dev.acuon.recipebook.data.local.RecipesDatabase
import dev.acuon.recipebook.data.remote.RecipesService
import dev.acuon.recipebook.data.remote.RetrofitHelper
import dev.acuon.recipebook.model.details.RecipeDetails
import dev.acuon.recipebook.repo.RecipesRepository
import dev.acuon.recipebook.utils.Constants
import dev.acuon.recipebook.viewmodel.RecipesViewModel
import dev.acuon.recipebook.viewmodel.RecipesViewModelFactory
import kotlinx.android.synthetic.main.activity_recipe_details.*

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var recipeID: String
    private lateinit var repository: RecipesRepository
    private lateinit var viewModel: RecipesViewModel

    private fun initialize() {
        val recipesService =
            RetrofitHelper.getInstanceForRecipeDetails(Constants.BASE_URL + recipeID + "/")
                .create(RecipesService::class.java)
        val database = RecipesDatabase.getDatabase(applicationContext)
        repository = RecipesRepository(recipesService, database, applicationContext)

        viewModel = ViewModelProvider(
            this,
            RecipesViewModelFactory(repository, 0)
        )[RecipesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        recipeID = intent.getStringExtra("recipeID")!!
        initialize()
        viewModel.recipeDetails.observe(this, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(recipe: RecipeDetails?) {
        Toast.makeText(this, recipe!!.title, Toast.LENGTH_SHORT).show()
        recipe_details_toolbar.title = recipe.title
        Glide.with(recipe_details_image).load(recipe.image).into(recipe_details_image)
        recipe_summary.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(recipe.summary, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(recipe.summary)
            }
    }

}