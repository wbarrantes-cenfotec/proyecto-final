package com.example.pokeapp.models

import com.example.pokeapp.extensions.Constants

data class PokemonResult(val name: String, val url: String) {

    fun getImageUrl(): String {
        return Constants.POKEMON_IMAGE_API_URL.replace("#ID#", this.getPokemonId().toString())
    }

    private fun getPokemonId(): Int {
        var id = url.replace(Constants.POKE_API_BASE_URL, "")
        id = id.replace("pokemon", "")
        return id.replace("/", "").toInt()
    }
}