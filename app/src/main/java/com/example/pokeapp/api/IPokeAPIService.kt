package com.example.pokeapp.api

import com.example.pokeapp.models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IPokeAPIService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int
    ) : Call<PokemonResponse>

}