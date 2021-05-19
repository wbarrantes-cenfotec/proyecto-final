package com.example.pokeapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokeapp.fragments.FavoritesFragment

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