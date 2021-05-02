package com.example.pokeapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.extensions.mapToVisibility
import com.example.pokeapp.fragments.PokemonDetailFragment
import com.example.pokeapp.fragments.PokemonListFragmentDirections
import com.example.pokeapp.models.Pokemon
import kotlinx.android.synthetic.main.fragment_pokemon_detail.view.*
import kotlinx.android.synthetic.main.pokemon_cell.view.*

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var pokemons: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(name: String, imageUrl: String, type: String, weakness: String,
                 description: String, evolution1: String,evolution1imageURL: String) {
            itemView.textViewPokemonName.text = name
            itemView.textViewPokemonType.text = type

            itemView.setOnClickListener {
                //itemView.textViewPokemonType.visibility = itemView.textViewPokemonType.isVisible.not().mapToVisibility()

                //val action = PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment3(Pokemon("Pikachu", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", "Electrico",
                //        "Tierra","Cuanto más potente es la energía eléctrica que genera este Pokémon, más suaves y elásticas se vuelven las bolsas de sus mejillas.",
                //        "Raichu","https://assets.pokemon.com/assets/cms2/img/pokedex/full/026.png"))

                val action = PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment3(Pokemon(name,imageUrl,type,weakness,description,evolution1,evolution1imageURL))
                Navigation.findNavController(itemView).navigate(action);

            }

            Glide.with(itemView.context)
                    .load(imageUrl)
                    .circleCrop()
                    .into(itemView.imageViewPokemonImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_cell, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bind(pokemon.name, pokemon.imageURL, pokemon.type,pokemon.weakness,pokemon.description,pokemon.evolution1,pokemon.evolution1imageURL)
    }

    override fun getItemCount(): Int = pokemons.size
}