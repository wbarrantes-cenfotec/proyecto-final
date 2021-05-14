package com.example.pokeapp.api

import com.example.pokeapp.models.EvolutionChain
import com.example.pokeapp.models.PokemonResponse
import com.example.pokeapp.models.PokemonDetail
import com.example.pokeapp.models.PokemonSpecies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPokeAPIService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int
    ) : Call<PokemonResponse>

    @GET("pokemon/{id}")
    fun getPokemonDetail(
        @Path("id") id: Int
    ) : Call<PokemonDetail>

    @GET("evolution-chain/{id}")
    fun getPokemonEvolutionDetail(
        @Path("id") id: Int
    ) : Call<EvolutionChain>

    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(
        @Path("id") id: Int
    ) : Call<PokemonSpecies>
}