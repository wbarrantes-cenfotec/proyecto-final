package com.example.pokeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.adapters.PokemonAdapter
import com.example.pokeapp.databinding.FragmentPokemonDetailBinding
import com.example.pokeapp.extensions.Constants
import com.example.pokeapp.models.ChainLink
import com.example.pokeapp.viewmodels.PokemonDetailViewModel
import java.util.*
import kotlin.collections.ArrayList


class PokemonDetailFragment : Fragment(R.layout.fragment_pokemon_detail) {

    //region Variables

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private val arguments: PokemonDetailFragmentArgs by navArgs()

    lateinit var viewModel: PokemonDetailViewModel

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

        viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)
        val url = arguments.pokemon.pokemonUrl
        viewModel.getPokemonDetail(url)

        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer { pokemon ->
            binding.textViewDirectionPokemonNameDetail.text = pokemon.name.toUpperCase(Locale.ROOT)

            Glide.with(binding.imageViewDirectionPokemon.context)
                .load(arguments.pokemon.imageURL)
                .circleCrop()
                .into(binding.imageViewDirectionPokemon)

            // fill types string based on typesArray
            val typesArray = pokemon.types
            var types = ""
            for (type in typesArray){
                types += type.type.name
                if (typesArray.indexOf(type) != typesArray.lastIndex){ types += ", "}
            }

            binding.textViewDirectionType.text = types
            //binding.textViewDirectionWeakness.text = pokemon.weight.toString()
        })

        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer { pokemon ->
            binding.textViewDirectionPokemonNameDetail.text = pokemon.name.toUpperCase(Locale.ROOT)

            Glide.with(binding.imageViewDirectionPokemon.context)
                .load(arguments.pokemon.imageURL)
                .circleCrop()
                .into(binding.imageViewDirectionPokemon)

            // fill types string based on typesArray
            val typesArray = pokemon.types
            var types = ""
            for (type in typesArray){
                types += type.type.name
                if (typesArray.indexOf(type) != typesArray.lastIndex){ types += ", "}
            }

            binding.textViewDirectionType.text = types

        })

        viewModel.pokemonDescriptionCompleted.observe(viewLifecycleOwner, Observer { pokemonDescription ->
            binding.textViewDirectionPokemonDescription.text = pokemonDescription.toString()
        })

        viewModel.pokemonEvolutionChain.observe(viewLifecycleOwner, Observer { pokemonEvolutionChain ->
            val evoChain = emptyArray<String>()
            var evoData: ChainLink = pokemonEvolutionChain.chain
            val list: List<ChainLink> = listOf(evoData)
            val count = list.flatMap { it.evolves_to }.groupingBy { it }.eachCount()

            //binding.textViewDirectionEvolution.text = pokemonEvolutionChain.chain.toString()
        })


    }
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

/*    fun transformEvolutionJson(chain: List<ChainLink>){

        val evoChain = emptyArray<String>()
        var evoData = chain

        return evoChain

    }*/

    //endregion Functions
}