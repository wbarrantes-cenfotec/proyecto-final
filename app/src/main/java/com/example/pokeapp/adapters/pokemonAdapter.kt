package com.example.pokeapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.extensions.mapToVisibility
import com.example.pokeapp.models.Pokemon
import kotlinx.android.synthetic.main.pokemon_cell.view.*

class pokemonAdapter : RecyclerView.Adapter<pokemonAdapter.ContactViewHolder>() {

    var pokemons: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(name: String, imageUrl: String, type: String) {
            itemView.textViewPokemonName.text = name
            itemView.textViewPokemonType.text = type

            itemView.setOnClickListener {
                itemView.textViewPokemonType.visibility = itemView.textViewPokemonType.isVisible.not().mapToVisibility()
            }

//            Picasso.get().load(imageUrl).into(itemView.imageView)
            Glide.with(itemView.context)
                    .load(imageUrl)
                    .circleCrop()
                    .into(itemView.imageViewPokemonImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_cell, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bind(pokemon.name, pokemon.imageURL, pokemon.type)
    }

    override fun getItemCount(): Int = pokemons.size
}