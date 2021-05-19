package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.adapters.PokemonEvolutionAdapter
import com.example.pokeapp.adapters.SliderImageAdapter
import com.example.pokeapp.databinding.FragmentPokemonDetailBinding
import com.example.pokeapp.viewmodels.FavoriteViewModel
import com.example.pokeapp.viewmodels.PokemonDetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.SliderView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class PokemonDetailFragment : Fragment(R.layout.fragment_pokemon_detail) {

    //region Variables

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private val arguments: PokemonDetailFragmentArgs by navArgs()

    private val disposable = CompositeDisposable()

    lateinit var viewModel: PokemonDetailViewModel
    val favViewModel : FavoriteViewModel by viewModels()

    private val adapter = PokemonEvolutionAdapter()

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
                .placeholder(R.drawable.placeholder)
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
                .placeholder(R.drawable.placeholder)
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

        binding.pokemonEvolutionRecyclerView.adapter = adapter

        viewModel.pokemonEvolutionList.observe(viewLifecycleOwner, Observer { pokemonEvolutionList ->

            adapter.pokemonEvolutions = pokemonEvolutionList

        })

        //toggle imageview resource on created
        var isFavorite:Int = 0
        disposable.add(
            favViewModel.isFavorite(arguments.pokemon.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    isFavorite = it
                    if ( it == 0 ){
                        binding.btnAddFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }else{
                        binding.btnAddFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }

                }
        )


        binding.btnAddFavorites.setOnClickListener{
            if ( isFavorite == 0) {
                favViewModel.createFavorite(
                    arguments.pokemon.name,
                    arguments.pokemon.pokemonUrl,
                    arguments.pokemon.imageURL
                )
                binding.btnAddFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                Snackbar.make(
                    view,
                    getString(R.string.added_to_favs),
                    Snackbar.LENGTH_SHORT
                ).show()
                isFavorite = 1
            } else {
                favViewModel.delFavorite(arguments.pokemon.name)
                binding.btnAddFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Snackbar.make(
                    view,
                    getString(R.string.removed_from_favs),
                    Snackbar.LENGTH_SHORT
                ).show()
                isFavorite = 0
            }
            //disposable.clear()
        }





    }
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = SliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        //imageSlider.isAutoCycle = true
        //imageSlider.startAutoCycle()
    }

    //endregion Functions
}