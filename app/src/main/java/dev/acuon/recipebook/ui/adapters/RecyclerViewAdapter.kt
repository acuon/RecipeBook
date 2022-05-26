package dev.acuon.recipebook.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.acuon.recipebook.R
import dev.acuon.recipebook.model.Details
import dev.acuon.recipebook.model.Result
import dev.acuon.recipebook.ui.clicklistener.ClickListener
import kotlinx.android.synthetic.main.recipe_card_layout.view.*

class RecipesAdapter(
    private val list: ArrayList<Result>,
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.recipe_card_layout,
                    parent,
                    false
                ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = list[position]
        holder.setData(recipe)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class FavoriteRecipeAdapter(
    private val list: ArrayList<Details>,
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.recipe_card_layout,
                    parent,
                    false
                ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = list[position]
        holder.setData(recipe)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


class RecipesViewHolder(itemView: View, private val clickListener: ClickListener) :
    RecyclerView.ViewHolder(itemView) {
    fun setData(recipe: Result) {
        itemView.apply {
            recipe_name.text = recipe.title
            Glide.with(recipe_image).load(recipe.image).into(recipe_image)
            setOnClickListener {
                clickListener.onClick(adapterPosition)
            }
            add_to_favorite.setOnClickListener {
                clickListener.addToFav(adapterPosition)
            }
        }
    }
    fun setData(recipe: Details) {
        itemView.apply {
            recipe_name.text = recipe.name
            Glide.with(recipe_image).load(recipe.image).into(recipe_image)
            setOnClickListener {
                clickListener.onClick(adapterPosition)
            }
            add_to_favorite.setOnClickListener {
                clickListener.addToFav(adapterPosition)
            }
        }
    }
}