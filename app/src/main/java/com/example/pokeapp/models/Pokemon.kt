package com.example.pokeapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pokemon(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("imageURL") val imageURL: String,
    @Expose @SerializedName("pokemonUrl") val pokemonUrl: String,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imageURL)
        parcel.writeString(pokemonUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}

data class PokemonDetail(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("weight") val weight: Int,
    @Expose @SerializedName("height") val height: Int,
    @Expose @SerializedName("types") val types:  List<Types>,
    @Expose @SerializedName("species") val species:  NamedApiResource
)

data class Types (
    @Expose @SerializedName("slot") val slot: String,
    @Expose @SerializedName("type") val type: Type
)

data class Type (
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("url") val url: String
)

data class PokemonSpecies(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("order") val order: Int,
    @Expose @SerializedName("genderRate") val genderRate: Int,
    @Expose @SerializedName("captureRate") val captureRate: Int,
    @Expose @SerializedName("baseHappiness") val baseHappiness: Int,
    @Expose @SerializedName("isBaby") val isBaby: Boolean,
    @Expose @SerializedName("isLegendary") val isLegendary: Boolean,
    @Expose @SerializedName("isMythical") val isMythical: Boolean,
    @Expose @SerializedName("hatchCounter") val hatchCounter: Int,
    @Expose @SerializedName("hasGenderDifferences") val hasGenderDifferences: Boolean,
    @Expose @SerializedName("formsSwitchable") val formsSwitchable: Boolean,
    @Expose @SerializedName("growthRate") val growthRate: NamedApiResource,
    @Expose @SerializedName("pokedexNumbers") val pokedexNumbers: List<PokemonSpeciesDexEntry>,
    @Expose @SerializedName("eggGroups") val eggGroups: List<NamedApiResource>,
    @Expose @SerializedName("color") val color: NamedApiResource,
    @Expose @SerializedName("shape") val shape: NamedApiResource,
    @Expose @SerializedName("evolvesFromSpecies") val evolvesFromSpecies: NamedApiResource,
    @Expose @SerializedName("evolution_chain") val evolutionChain: ApiResource,
    @Expose @SerializedName("habitat") val habitat: NamedApiResource?,
    @Expose @SerializedName("generation") val generation: NamedApiResource,
    @Expose @SerializedName("names") val names: List<Name>,
    @Expose @SerializedName("palParkEncounters") val palParkEncounters: List<PalParkEncounterArea>,
    @Expose @SerializedName("formDescriptions") val formDescriptions: List<Description>,
    @Expose @SerializedName("genera") val genera: List<Genus>,
    @Expose @SerializedName("varieties") val varieties: List<PokemonSpeciesVariety>,
    @Expose @SerializedName("flavor_text_entries") val flavor_text_entries: List<PokemonSpeciesFlavorText>
)

data class PokemonSpeciesFlavorText(
    @Expose @SerializedName("flavor_text") val flavorText: String,
    @Expose @SerializedName("language") val language: NamedApiResource,
    @Expose @SerializedName("version") val version: NamedApiResource
)

data class Genus(
    val genus: String,
    val language: NamedApiResource
)

data class PokemonSpeciesDexEntry(
    val entryNumber: Int,
    val pokedex: NamedApiResource
)

data class PalParkEncounterArea(
    val baseScore: Int,
    val rate: Int,
    val area: NamedApiResource
)

data class PokemonSpeciesVariety(
    val isDefault: Boolean,
    val pokemon: NamedApiResource
)

data class Description(
    val description: String,
    val language: NamedApiResource
)

data class ApiResource(
    val url: String
) : ResourceSummary {
    constructor(category: String, id: Int) : this(resourceUrl(id, category))

    override val category by lazy { urlToCat(url) }
    override val id by lazy { urlToId(url) }
}

private fun urlToId(url: String): Int {
    return "/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isDigit() || it == '-' }.toInt()
}

private fun urlToCat(url: String): String {
    return "/[a-z\\-]+/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isLetter() || it == '-' }
}

private fun resourceUrl(id: Int, category: String): String {
    return "/api/v2/$category/$id/"
}
