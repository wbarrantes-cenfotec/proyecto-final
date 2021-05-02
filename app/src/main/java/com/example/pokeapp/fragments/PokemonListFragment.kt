package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.adapters.PokemonAdapter
import com.example.pokeapp.models.Pokemon
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val adapter = PokemonAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonsRecyclerView.adapter = adapter
        pokemonsRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        adapter.pokemons = getDummyPokemons()
    }

    private fun getDummyPokemons() : List<Pokemon> {
        return mutableListOf(
            Pokemon("Pikachu", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", "Electrico",
                "Tierra","Cuanto más potente es la energía eléctrica que genera este Pokémon, más suaves y elásticas se vuelven las bolsas de sus mejillas.",
            "Raichu","https://assets.pokemon.com/assets/cms2/img/pokedex/full/026.png"),
            Pokemon("Charmander", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png", "Fuego",
            "Agua-Tierra-Roca","Prefiere las cosas calientes. Dicen que cuando llueve le sale vapor de la punta de la cola.",
            "Charmeleon","https://assets.pokemon.com/assets/cms2/img/pokedex/full/005.png"),
            Pokemon("Mew", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/151.png", "Psiquico",
            "Fantasma-Siniestro-Bicho","Si se observa a través de un microscopio, puede distinguirse cuán corto, fino y delicado es el pelaje de este Pokémon.",
            "Este Pokémon no evoluciona",""),
            Pokemon("Zorua", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/570.png", "Siniestro",
            "Hada-Bicho-Lucha","Al parecer, fue su carácter miedoso lo que le llevó a desarrollar la capacidad de transformarse.",
            "Zoroark","https://assets.pokemon.com/assets/cms2/img/pokedex/full/571.png"),
            Pokemon("Drampa", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/780.png", "Dragon",
            "Hada-Lucha-Hielo-Dragon","Habita en montañas de más de 3000 m de altura. En ocasiones se acerca a los pueblos para jugar con los niños.",
            "Este Pokémon no evoluciona",""),
            Pokemon("Golisopod", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/768.png", "Bicho-Agua",
            "Volador-Electrico-Roca","Hace lo que sea por conseguir la victoria. Si el rival se descuida, aprovecha para asestarle un golpe letal con sus pequeñas garras frontales.",
            "Esta es la ultima evolucion del Pokemon Wimpod","")
        )
    }
}