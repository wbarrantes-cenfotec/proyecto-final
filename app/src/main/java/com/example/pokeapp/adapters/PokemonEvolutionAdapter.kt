package com.example.pokeapp.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.databinding.EvolutionCellBinding
import com.example.pokeapp.models.PokemonSpecies
import java.util.*
import com.example.pokeapp.extensions.Constants
import com.example.pokeapp.fragments.PokemonDetailFragmentDirections
import com.example.pokeapp.fragments.PokemonListFragmentDirections
import com.example.pokeapp.models.Pokemon

class PokemonEvolutionAdapter : RecyclerView.Adapter<PokemonEvolutionAdapter.PokemonEvolutionViewHolder>() {

    var pokemonEvolutions: List<Any> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonEvolutionViewHolder(private val binding: EvolutionCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemonEvolutions: List<Any>) {

            var pokemonEvolutionImageUrl = ""
            var pokemonEvolutionBaseURL = ""
            var id = 0
            for (element in pokemonEvolutions) {
                id = (element as PokemonSpecies).id
                pokemonEvolutionImageUrl = Constants.POKEMON_IMAGE_API_URL.replace("#ID#", id.toString())
                pokemonEvolutionBaseURL = Constants.POKEGET_API_BASE_URL.replace("#ID#", id.toString())

                // set the pokemon name
                binding.textViewPokemonEvolutionName.text = element.name.toUpperCase(Locale.ROOT)

                // load the pokemon image
                Glide.with(binding.root)
                    .load(pokemonEvolutionImageUrl)
                    .circleCrop()
                    .into(binding.imageViewPokemonEvolutionImage)

                // set the click event listener
                binding.root.setOnClickListener {
                    val action = PokemonDetailFragmentDirections
                        .actionPokemonDetailFragment2Self(
                            Pokemon(element.name.toUpperCase(),
                                pokemonEvolutionImageUrl,
                                pokemonEvolutionBaseURL
                            )
                        )

                    Navigation.findNavController(binding.root).navigate(action)
                }
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonEvolutionViewHolder {
        val view = EvolutionCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonEvolutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonEvolutionViewHolder, position: Int) {
        holder.bind(listOf(pokemonEvolutions[position]))
    }

    override fun getItemCount(): Int = pokemonEvolutions.size
}