package com.example.pokeapp.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.api.IPokeAPIService
import com.example.pokeapp.extensions.Constants
import com.example.pokeapp.models.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class PokemonDetailViewModel : ViewModel() {

    val pokemonDetail = MutableLiveData<PokemonDetail>()
    var pokemonDescriptionCompleted = MutableLiveData<String>()
    var pokemonEvolutionList = MutableLiveData<List<Any>>()

    private var pokeApiService: IPokeAPIService

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build();

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.POKE_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

                        // GetPokemonEvolutionChain
                        getEvolutionChain(pokemonSpecies.evolutionChain.url)

                        // GetPokemonDescription
                        pokemonDescriptionCompleted.postValue(getPokemonDescriptionText(pokemonSpecies.flavor_text_entries)!!)

                    }
                }

                override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                }
            })
    }


    @SuppressLint("CheckResult")
    private fun getPokemonEvolutionSpecies(speciesIDs: ArrayList<Int>){

        val requests = ArrayList<Observable<PokemonSpecies>>()
        for (specieID in speciesIDs) {
            requests.add(pokeApiService.getPokemonSpeciesObservable(specieID))
        }
        Observable
            .zip(requests) { PokemonEvolutionResults ->
                // do something with those results and emit new event
                //Any() // <-- Here we emit just new empty Object(), but you can emit anything
                pokemonEvolutionList.postValue(PokemonEvolutionResults.toList())
                Log.d("Result1: ",PokemonEvolutionResults.toString())
            }
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Result2: ",it.toString())
            }) {
                //Do something on error completion of requests
                Log.d("Error: ",it.toString())


            }

    }


    fun getEvolutions(evolutionChain: MutableList<ChainLink>){

        var speciedIDArray : ArrayList<Int> = arrayListOf()
        var id = 0
            for (evolution in evolutionChain) {
                id = getSpeciesId(evolution.species.url)
                speciedIDArray.add(id)
        }
        getPokemonEvolutionSpecies(speciedIDArray)
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
                        //pokemonEvolutionChain.postValue(getEvolutions(evolutionList)!!)

                        //pokemonEvolutionList.postValue(getEvolutions(evolutionList))
                        getEvolutions(evolutionList)


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