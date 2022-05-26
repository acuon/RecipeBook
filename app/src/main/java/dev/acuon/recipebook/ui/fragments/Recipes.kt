package dev.acuon.recipebook.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.recipebook.R
import dev.acuon.recipebook.RecipesApplication
import dev.acuon.recipebook.data.local.RecipesDatabase
import dev.acuon.recipebook.data.remote.RecipesService
import dev.acuon.recipebook.data.remote.RetrofitHelper
import dev.acuon.recipebook.model.Result
import dev.acuon.recipebook.repo.RecipesRepository
import dev.acuon.recipebook.ui.RecipeDetailsActivity
import dev.acuon.recipebook.ui.clicklistener.ClickListener
import dev.acuon.recipebook.ui.adapters.RecipesAdapter
import dev.acuon.recipebook.utils.Constants
import dev.acuon.recipebook.viewmodel.RecipesViewModel
import dev.acuon.recipebook.viewmodel.RecipesViewModelFactory
import kotlinx.android.synthetic.main.fragment_recipes.*

class Recipes : Fragment(R.layout.fragment_recipes), ClickListener {

    private lateinit var viewModel: RecipesViewModel
    private lateinit var adapter: RecipesAdapter
    private var list = arrayListOf<Result>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
//        getData()
        setAdapter()
    }

    private fun setAdapter() {
        getData()
//        dummyData()
        toast("after - "+list.size.toString())
        adapter = RecipesAdapter(list, this)
        recipes_rcv.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recipes_rcv.layoutManager = layoutManager
    }

    private fun dummyData() {
        for (i in 1..10) {
            list.add(
                Result(
                    i,
                    Constants.DUMMY_IMAGE_LINK,
                    "jpg",
                    "recipe - $i"
                )
            )
        }
    }

    private fun initializeViewModel() {
        val repository = (requireActivity().application as RecipesApplication).repository
        viewModel = ViewModelProvider(
            this,
            RecipesViewModelFactory(repository, 0)
        )[RecipesViewModel::class.java]
    }

    private fun getData() {
        list.clear()
        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            list.addAll(it)
            toast("taking - "+list.size.toString())
            adapter.notifyDataSetChanged()
        })
        toast(list.size.toString())
    }

    override fun onClick(position: Int) {
        toast("${list[position].title}  clicked")
        val intent = Intent(requireContext(), RecipeDetailsActivity::class.java)
        intent.putExtra("recipeID", list[position].id.toString())
        startActivity(intent)
    }

    override fun addToFav(position: Int) {
        val recipesService =
            RetrofitHelper.getInstanceForRecipeDetails(Constants.BASE_URL + list[position].id + "/")
                .create(RecipesService::class.java)
        val database = RecipesDatabase.getDatabase(requireActivity().applicationContext)
        val repository = RecipesRepository(recipesService, database, requireActivity().applicationContext)

        val tempViewModel = ViewModelProvider(
            this,
            RecipesViewModelFactory(repository, 0)
        )[RecipesViewModel::class.java]
        tempViewModel.saveToFavorites()

        toast("${list[position].title}  added to favorites")
    }

    private fun toast(str: String) {
        Toast.makeText(
            requireContext(), str,
            Toast.LENGTH_SHORT
        ).show()
    }

}