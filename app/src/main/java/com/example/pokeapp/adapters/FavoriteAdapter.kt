package com.example.pokeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.pokeapp.databinding.PokemonCellBinding
import com.example.pokeapp.db.entities.Favorite
import com.example.pokeapp.fragments.FavoritesFragment
import com.example.pokeapp.fragments.FavoritesFragmentDirections
import com.example.pokeapp.models.Pokemon


class FavoriteAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position==0){
            FavoritesFragment()
        }else{
            FavoritesFragment()
        }

    }
}

class FavoriteListAdapter:RecyclerView.Adapter<FavoriteListAdapter.FavoritesViewHolder>(){
    var favoriteList: List<Favorite> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    inner class FavoritesViewHolder(private val binding: PokemonCellBinding): RecyclerView.ViewHolder(binding.root ){
        fun bind(favorite: Favorite){
            binding.textViewPokemonName.text = favorite.favName.toUpperCase()
            binding.root.setOnClickListener {

                val action = FavoritesFragmentDirections.actionFavoritesFragmentToPokemonDetailFragment2(
                        Pokemon(favorite.favName.toUpperCase(),
                            favorite.img,
                            favorite.url
                        )
                    )

                Navigation.findNavController(binding.root).navigate(action)
            }
            // load the pokemon image
            Glide.with(binding.root)
                .load(favorite.img)
                .circleCrop()
                .into(binding.imageViewPokemonImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
       val binding = PokemonCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size
}