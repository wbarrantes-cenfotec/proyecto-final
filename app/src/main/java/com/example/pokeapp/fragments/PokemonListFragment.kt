package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokeapp.R
import com.example.pokeapp.adapters.PokemonAdapter
import com.example.pokeapp.databinding.FragmentPokemonListBinding
import com.example.pokeapp.viewmodels.PokemonListViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_pokemon_list.view.*
import java.util.concurrent.TimeUnit


class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding get() = _binding!!

    private val disposable = CompositeDisposable()

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
        disposable.clear()
        _binding = null
    }

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pokemonsRecyclerView.adapter = adapter

        // subscribe to the event that is dispatch
        // from the viewModel when the request is completed
        viewModel.getPokemonList().observe(viewLifecycleOwner) {
            adapter.pokemons = it
        }

        Thread(Runnable {
            disposable.add(
                binding.searchBox.textChanges()
                    .skipInitialValue()
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .map { it.toString() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.isEmpty()) {
                            viewModel.makeAPIRequest()

                        } else {
                            //adapter.pokemons = viewModel.searchPokemon(binding.textInputLayoutSearch.searchBox.text.toString()).value!!
                            //viewModel.findPokemon(binding.textInputLayoutSearch.searchBox.text.toString())
                           //runOnUiThread{
                               viewModel.makeAPIRequest2(binding.textInputLayoutSearch.searchBox.text.toString())
                              //  val poke = PokemonResult("test","https://pokeapi.co/api/v2/pokemon/25/")
                              //  val testList: List<PokemonResult> = listOf(poke)
                              //  adapter.pokemons = testList
                            // }
                        }
                    }
            )

        }).start()






    }
}