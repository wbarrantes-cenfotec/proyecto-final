package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.databinding.FragmentPokemonDetailBinding

class PokemonDetailFragment : Fragment(R.layout.fragment_pokemon_detail) {

    //region Variables

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private val arguments: PokemonDetailFragmentArgs by navArgs()

    //endregion Variables

    //region Functions

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.imageViewDirectionPomemon.context)
                .load(arguments.pokemon.imageURL)
                .circleCrop()
                .into(binding.imageViewDirectionPomemon)

        binding.textViewDirectionPokemonNameDetail.text = arguments.pokemon.name
        binding.textViewDirectionType.text = arguments.pokemon.type
        binding.textViewDirectionWeakness.text = arguments.pokemon.weakness
        binding.textViewDirectionPokemonDescription.text = arguments.pokemon.description
        binding.textViewDirectionEvolution.text = arguments.pokemon.evolution1

        Glide.with(binding.imageViewDirectionPokemonEvolution.context)
                .load(arguments.pokemon.evolution1imageURL)
                .circleCrop()
                .into(binding.imageViewDirectionPokemonEvolution)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    //endregion Functions
}