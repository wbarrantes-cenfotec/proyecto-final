package com.example.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.pokeapp.R
import com.example.pokeapp.adapters.pokemonAdapter
import com.example.pokeapp.models.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class MainActivity : AppCompatActivity() {

    private val adapter = pokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonsRecyclerView.adapter = adapter
        pokemonsRecyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }

    private fun getDummyPokemons() : List<Pokemon> {
        return mutableListOf(
            Pokemon("Pikachu", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", "Electrico"),
            Pokemon("Charmander", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png", "Fuego"),
            Pokemon("Mew", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/151.png", "Psiquico")
        )
    }
}