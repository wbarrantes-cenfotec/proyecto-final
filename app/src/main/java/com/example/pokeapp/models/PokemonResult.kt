package com.example.pokeapp.models

data class PokemonResult(val name: String, val url: String) {

    fun getImageUrl(): String {
        return "https://pokeres.bastionbot.org/images/pokemon/${this.getPokemonId()}.png"
    }

    private fun getPokemonId(): Int {

        val id = url.replace("https://pokeapi.co/api/v2/pokemon/", "")
        return id.replace("/", "").toInt()
    }
}