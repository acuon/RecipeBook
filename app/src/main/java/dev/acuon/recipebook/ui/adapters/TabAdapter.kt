package dev.acuon.recipebook.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.acuon.recipebook.ui.fragments.MyFavourites
import dev.acuon.recipebook.ui.fragments.Recipes

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return Recipes()
            1 -> return MyFavourites()
        }
        return Recipes()
    }
}