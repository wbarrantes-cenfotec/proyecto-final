package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import kotlinx.android.synthetic.main.fragment_pokemon_detail.view.*
import kotlinx.android.synthetic.main.pokemon_cell.view.*

class PokemonDetailFragment : Fragment(R.layout.fragment_pokemon_detail) {

    private val arguments: PokemonDetailFragmentArgs by navArgs()
    private lateinit var pokemonImageView: ImageView
    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonTypeTextView: TextView
    private lateinit var pokemonWeaknessTextView: TextView
    private lateinit var pokemonDescriptionTextView: TextView
    private lateinit var pokemonEvolution1TextView: TextView
    private lateinit var pokemonEvolution1ImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonImageView = view.findViewById(R.id.imageViewDirectionPomemon)
        Glide.with(pokemonImageView.context)
                .load(arguments.pokemon.imageURL)
                .circleCrop()
                .into(pokemonImageView.imageViewDirectionPomemon)

        pokemonNameTextView = view.findViewById(R.id.textViewDirectionPokemonNameDetail)
        pokemonNameTextView.text = arguments.pokemon.name

        pokemonTypeTextView = view.findViewById(R.id.textViewDirectionType)
        pokemonTypeTextView.text = arguments.pokemon.type

        pokemonWeaknessTextView = view.findViewById(R.id.textViewDirectionWeakness)
        pokemonWeaknessTextView.text = arguments.pokemon.weakness

        pokemonDescriptionTextView = view.findViewById(R.id.textViewDirectionPokemonDescription)
        pokemonDescriptionTextView.text = arguments.pokemon.description

        pokemonEvolution1TextView = view.findViewById(R.id.textViewDirectionEvolution)
        pokemonEvolution1TextView.text = arguments.pokemon.evolution1

        pokemonEvolution1ImageView = view.findViewById(R.id.imageViewDirectionPokemonEvolution)
        Glide.with(pokemonEvolution1ImageView.context)
                .load(arguments.pokemon.evolution1imageURL)
                .circleCrop()
                .into(pokemonEvolution1ImageView.imageViewDirectionPokemonEvolution)


    }

}