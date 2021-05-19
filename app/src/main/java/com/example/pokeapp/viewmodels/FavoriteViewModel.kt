package com.example.pokeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.db.entities.Favorite
import com.example.pokeapp.repositories.FavoriteRepository
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application){
    private var repository: FavoriteRepository = FavoriteRepository(application.applicationContext)

    fun createFavorite(name: String, passurl:String,image:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(Favorite(favName = name,url=passurl,img = image))
        }
    }

    fun isFavorite(name: String): Observable<Int> {
         return repository.isFavorite(name)
    }

    fun getFavorites(): Observable<List<Favorite>> {
        return repository.getFavorites()
    }

    fun delFavorite(name:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delFavorite(name)
        }
    }

}
