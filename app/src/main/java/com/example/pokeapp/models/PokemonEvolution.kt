package com.example.pokeapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

private fun urlToId(url: String): Int {
    return "/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isDigit() || it == '-' }.toInt()
}

private fun urlToCat(url: String): String {
    return "/[a-z\\-]+/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isLetter() || it == '-' }
}

private fun resourceUrl(id: Int, category: String): String {
    return "/api/v2/$category/$id/"
}

interface ResourceSummary {
    val id: Int
    val category: String
}

data class NamedApiResource(
    val name: String,
    val url: String
) : ResourceSummary {
    constructor(name: String, category: String, id: Int) : this(name, resourceUrl(id, category))

    override val category by lazy { urlToCat(url) }
    override val id by lazy { urlToId(url) }
}

data class EvolutionChain(
    val id: Int,
    val babyTriggerItem: NamedApiResource?,
    val chain: ChainLink
)

data class ChainLink(
    @Expose @SerializedName("isBaby") val isBaby:  Boolean,
    @Expose @SerializedName("species") val species:  NamedApiResource,
    @Expose @SerializedName("evolution_details") val evolutionDetails:  List<EvolutionDetail>,
    @Expose @SerializedName("evolves_to") val evolves_to:  List<ChainLink>
)

data class EvolutionDetail(
    val trigger: NamedApiResource,
    val item: NamedApiResource? = null,
    val gender: Int? = null,
    val heldItem: NamedApiResource? = null,
    val knownMove: NamedApiResource? = null,
    val knownMoveType: NamedApiResource? = null,
    val location: NamedApiResource? = null,
    val minLevel: Int? = null,
    val minHappiness: Int? = null,
    val minBeauty: Int? = null,
    val minAffection: Int? = null,
    val partySpecies: NamedApiResource? = null,
    val partyType: NamedApiResource? = null,
    val relativePhysicalStats: Int? = null,
    val timeOfDay: String = "",
    val tradeSpecies: NamedApiResource? = null,
    val needsOverworldRain: Boolean = false,
    val turnUpsideDown: Boolean = false

)

data class EvolutionTrigger(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemonSpecies: List<NamedApiResource>
)


data class Name(
    val name: String,
    val language: NamedApiResource
)