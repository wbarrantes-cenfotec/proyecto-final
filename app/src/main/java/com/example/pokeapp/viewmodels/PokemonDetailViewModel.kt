package com.example.pokeapp.viewmodels

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
    val pokemonEvolutionList = MutableLiveData<List<ChainLink>>()
    private var pokeApiService: IPokeAPIService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.POKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokeApiService = retrofit.create(IPokeAPIService::class.java)
    }

    fun getPokemonDetail(pokemonURL: String) {

        val id = getPokemonId(pokemonURL)

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

        val id = getSpeciesId(speciesURL)

        pokeApiService.getPokemonSpecies(id)
            .enqueue(object: Callback<PokemonSpecies> {
                override fun onResponse(
                    call: Call<PokemonSpecies>,
                    response: Response<PokemonSpecies>
                ) {
                    response.body()?.let { pokemonSpecies ->
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

        val id = getChainId(chainURL)

        pokeApiService.getPokemonEvolutionDetail(id)
            .enqueue(object: Callback<EvolutionChain> {
                override fun onResponse(
                    call: Call<EvolutionChain>,
                    response: Response<EvolutionChain>
                ) {
                    response.body()?.let { pokemonEvolution ->

                        // CODE to generate the proper evolution list
                        val evolutionList: MutableList<ChainLink> = arrayListOf()
                        var evolutionData: ChainLink = pokemonEvolution.chain
                        var continueIteration = true

                        do {
                            val numberOfEvolutions: Int = evolutionData.evolves_to.size
                            val hasMultipleEvolutions: Boolean = numberOfEvolutions > 1

                            // add the evolution to the list
                            evolutionList.add(ChainLink(
                                    evolutionData.isBaby,
                                    evolutionData.species,
                                    evolutionData.evolutionDetails,
                                    evolutionData.evolves_to
                            ))

                            // in case it has multiple evolutions iterate over each one and add to the list
                            if (hasMultipleEvolutions) {
                                for (i in 1..numberOfEvolutions) {
                                    evolutionList.add(ChainLink(
                                            evolutionData.evolves_to[i].isBaby,
                                            evolutionData.evolves_to[i].species,
                                            evolutionData.evolves_to[i].evolutionDetails,
                                            evolutionData.evolves_to[i].evolves_to
                                    ))
                                }
                            }

                            // check if has more evolutions in the chain
                            if (evolutionData.evolves_to.isNotEmpty())
                            {
                                // update the evolution data object
                                evolutionData = evolutionData.evolves_to[0]
                            }
                            else {
                                continueIteration = false
                            }

                        } while (continueIteration)

                        //TODO: post the value to the fragment using the evolution list that we create manually
                        //pokemonEvolutionList.postValue(evolutionList)
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

        for (i in pokemonSpeciesFlavor) {
            if(i.language.name == "es") {
                val isElementInArray = descriptionArray.contains(i.flavorText)

                if (!isElementInArray) {
                    descriptionArray.add(i.flavorText)
                }
            }
        }

        for (j in descriptionArray) {
            if (count < 2) { //show only two lines for the description
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