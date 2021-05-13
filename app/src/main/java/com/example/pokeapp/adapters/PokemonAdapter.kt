package com.example.pokeapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.databinding.PokemonCellBinding
import com.example.pokeapp.fragments.PokemonListFragmentDirections
import com.example.pokeapp.models.Pokemon
import com.example.pokeapp.models.PokemonResult
import java.util.*

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var pokemons: List<PokemonResult> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonViewHolder(private val binding: PokemonCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: PokemonResult) {
            // get the pokemon image url (based on the original url)
            val pokemonImageUrl = pokemon.getImageUrl()

            // set the pokemon name
            binding.textViewPokemonName.text = pokemon.name.toUpperCase(Locale.ROOT)

            // set the click event listener
            binding.root.setOnClickListener {

                val action = PokemonListFragmentDirections
                    .actionPokemonListFragment2ToPokemonDetailFragment2(
                        Pokemon(pokemon.name.toUpperCase(),
                            pokemonImageUrl,
                            pokemon.url
                        )
                    )

                Navigation.findNavController(binding.root).navigate(action)
            }

            // load the pokemon image
            Glide.with(binding.root)
                .load(pokemonImageUrl)
                .circleCrop()
                .into(binding.imageViewPokemonImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = PokemonCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemons[position])
    }

    override fun getItemCount(): Int = pokemons.size
}