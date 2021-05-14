package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.pokeapp.R
import com.example.pokeapp.adapters.PokemonAdapter
import com.example.pokeapp.databinding.FragmentPokemonListBinding
import com.example.pokeapp.viewmodels.PokemonListViewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding get() = _binding!!

    private val viewModel: PokemonListViewModel by viewModels()
    private val adapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // make the call to the server
        viewModel.makeAPIRequest()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pokemonsRecyclerView.adapter = adapter

        // subscribe to the event that is dispatch
        // from the viewModel when the request is completed
        viewModel.getPokemonList().observe(viewLifecycleOwner) {
            adapter.pokemons = it
        }
    }
}