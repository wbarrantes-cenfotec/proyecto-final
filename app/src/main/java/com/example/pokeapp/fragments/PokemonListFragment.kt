package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.adapters.PokemonAdapter
import com.example.pokeapp.models.Pokemon
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val adapter = PokemonAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonsRecyclerView.adapter = adapter
        pokemonsRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        adapter.pokemons = getDummyPokemons()
    }

    private fun getDummyPokemons() : List<Pokemon> {
        return mutableListOf(
            Pokemon("Pikachu", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", "Electrico"),
            Pokemon("Charmander", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png", "Fuego"),
            Pokemon("Mew", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/151.png", "Psiquico")
        )
    }
}