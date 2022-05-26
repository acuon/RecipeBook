package dev.acuon.recipebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import dev.acuon.recipebook.R
import dev.acuon.recipebook.ui.adapters.TabAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapter()
    }

    private fun setAdapter() {
        val adapter = TabAdapter(supportFragmentManager, lifecycle)
        view_pager.adapter = adapter
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            when(position) {
                0 -> tab.text = "Recipes"
                1 -> tab.text = "My Favourites"
            }
        }.attach()
    }
}