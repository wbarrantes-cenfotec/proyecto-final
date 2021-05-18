package com.example.pokeapp.api

import com.example.pokeapp.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

interface IPokeAPIService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int
    ) : Call<PokemonResponse>

    @GET("pokemon-form/{id}")
    fun searchPokemon(
        @Query("id") name: Int
    ) : Call<PokemonSearch>

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

    @GET("pokemon-species/{id}")
    fun getPokemonSpeciesObservable(
        @Path("id") id: Int
    ) : Observable<PokemonSpecies>
}