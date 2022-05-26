package dev.acuon.recipebook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dev.acuon.recipebook.R
import dev.acuon.recipebook.RecipesApplication
import dev.acuon.recipebook.model.Details
import dev.acuon.recipebook.ui.adapters.FavoriteRecipeAdapter
import dev.acuon.recipebook.ui.clicklistener.ClickListener
import dev.acuon.recipebook.viewmodel.RecipesViewModel
import dev.acuon.recipebook.viewmodel.RecipesViewModelFactory
import kotlinx.android.synthetic.main.fragment_my_favourites.*

class MyFavourites : Fragment(R.layout.fragment_my_favourites), ClickListener {

    private lateinit var viewModel: RecipesViewModel
    private lateinit var adapter: FavoriteRecipeAdapter
    private var list = arrayListOf<Details>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        setAdapter()
    }

    private fun setAdapter() {
        getData()
        adapter = FavoriteRecipeAdapter(list, this)
        fav_rcv.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        fav_rcv.layoutManager = layoutManager
    }

    private fun getData() {
        viewModel.favoriteRecipes.observe(viewLifecycleOwner, Observer {
            list.addAll(it)
        })
    }

    private fun initializeViewModel() {
        val repository = (requireActivity().application as RecipesApplication).repository
        viewModel = ViewModelProvider(
            this,
            RecipesViewModelFactory(repository, 0)
        )[RecipesViewModel::class.java]
    }

    override fun onClick(position: Int) {

    }

    override fun addToFav(position: Int) {

    }

}