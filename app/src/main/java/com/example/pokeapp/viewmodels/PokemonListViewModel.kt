package com.example.pokeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeapp.api.IPokeAPIService
import com.example.pokeapp.extensions.Constants
import com.example.pokeapp.models.PokemonResponse
import com.example.pokeapp.models.PokemonResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel : ViewModel() {

    private val pokemonList = MutableLiveData<List<PokemonResult>>()
    private var pokeApiService: IPokeAPIService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.POKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokeApiService = retrofit.create(IPokeAPIService::class.java)
    }

    fun makeAPIRequest() {

        pokeApiService.getPokemonList(Constants.TOTAL_POKEMONS)
            .enqueue(object: Callback<PokemonResponse>{
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
                ) {
                    response.body()?.let {
                        // notify to the subscribers
                        pokemonList.postValue(it.results)
                    }
                }

                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                }
            })
    }

    fun getPokemonList(): LiveData<List<PokemonResult>> {
        return pokemonList
    }
}