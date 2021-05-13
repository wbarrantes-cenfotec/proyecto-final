package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.api.IPokeAPIService
import com.example.pokeapp.extensions.Constants
import com.example.pokeapp.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonDetailViewModel : ViewModel() {

    val pokemonDetail = MutableLiveData<PokemonDetail>()
    var pokemonDescriptionCompleted = MutableLiveData<String>()
    val pokemonDetailSpecies = MutableLiveData<PokemonSpecies>()
    val pokemonEvolutionChain = MutableLiveData<EvolutionChain>()
    private var pokeApiService: IPokeAPIService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.POKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokeApiService = retrofit.create(IPokeAPIService::class.java)
    }

    fun getPokemonDetail(pokemonURL: String) {
        var id = getPokemonId(pokemonURL)
        pokeApiService.getPokemonDetail(id)
            .enqueue(object: Callback<PokemonDetail> {
                override fun onResponse(
                    call: Call<PokemonDetail>,
                    response: Response<PokemonDetail>
                ) {
                    response.body()?.let {pokemon ->
                        pokemonDetail.postValue(pokemon)
                        // GetPokemonSpecies
                        getPokemonSpecies(pokemon.species.url)
                    }
                }
                override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                }
            })
    }

    fun getPokemonSpecies(speciesURL: String) {
        var id = getSpeciesId(speciesURL)
        pokeApiService.getPokemonSpecies(id)
            .enqueue(object: Callback<PokemonSpecies> {
                override fun onResponse(
                    call: Call<PokemonSpecies>,
                    response: Response<PokemonSpecies>
                ) {
                    response.body()?.let {pokemonSpecies ->
                        pokemonDetailSpecies.postValue(pokemonSpecies)
                        // GetPokemonEvolutionChain
                        getEvolutionChain(pokemonSpecies.evolutionChain.url)
                        // GetPokemonDescription
                        pokemonDescriptionCompleted.postValue(getPokemonDescriptionText(pokemonSpecies.flavor_text_entries))
                    }
                }

                override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                }
            })
    }

    fun getEvolutionChain(chainURL: String) {
        var id = getChainId(chainURL)
        pokeApiService.getPokemonEvolutionDetail(id)
            .enqueue(object: Callback<EvolutionChain> {
                override fun onResponse(
                    call: Call<EvolutionChain>,
                    response: Response<EvolutionChain>
                ) {
                    response.body()?.let {pokemonEvolution ->
                        pokemonEvolutionChain.postValue(pokemonEvolution)
                    }
                }

                override fun onFailure(call: Call<EvolutionChain>, t: Throwable) {
                }
            })
    }

    fun getPokemonDescriptionText(pokemonSpeciesFlavor: List<PokemonSpeciesFlavorText>): String? {
        val descriptionArray: MutableList<String> = ArrayList()
        var description = ""
        var count = 0
        for (i in pokemonSpeciesFlavor){
            if(i.language.name == "es"){
                val isElementInArray = descriptionArray.contains(i.flavorText)
                if(!isElementInArray){descriptionArray.add(i.flavorText)}
            }
        }
        for (j in descriptionArray){
            if(count < 2){ //show only two lines for the description
                description += j
                count ++
            }

        }
        return description
    }

      private fun getPokemonId(pokemonURL: String): Int {
        var id = pokemonURL.replace(Constants.POKE_API_BASE_URL, "")
        id = id.replace("pokemon", "")
        return id.replace("/", "").toInt()
    }

    private fun getSpeciesId(speciesURL: String): Int {
        var id = speciesURL.replace(Constants.POKE_API_BASE_URL, "")
        id = id.replace("pokemon-species", "")
        return id.replace("/", "").toInt()
    }

    private fun getChainId(chainURL: String): Int {
        var id = chainURL.replace(Constants.POKE_API_BASE_URL, "")
        id = id.replace("evolution-chain", "")
        return id.replace("/", "").toInt()
    }

}