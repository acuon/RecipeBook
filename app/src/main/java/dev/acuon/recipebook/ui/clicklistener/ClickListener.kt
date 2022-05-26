package dev.acuon.recipebook.ui.clicklistener

interface ClickListener {
    fun onClick(position: Int)
    fun addToFav(position: Int)
}