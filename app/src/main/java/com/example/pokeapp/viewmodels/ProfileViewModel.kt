package com.example.pokeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pokeapp.db.entities.Trainer
import com.example.pokeapp.repositories.FavoriteRepository
import com.example.pokeapp.repositories.TrainerRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

interface IProfileViewModelInputs {
    val favoriteButtonClicked: Observer<Unit>
}

interface IProfileViewModelOutputs {
    val trainer: Observable<Trainer>
    val totalFavorites: Observable<Int>
}

interface IProfileViewModelType {
    val inputs: IProfileViewModelInputs
    val outputs: IProfileViewModelOutputs
}

class ProfileViewModel(application: Application) : AndroidViewModel(application),
    IProfileViewModelInputs,
    IProfileViewModelOutputs,
    IProfileViewModelType {

    // region Variables

    private var trainerRepository: TrainerRepository = TrainerRepository(application.applicationContext)
    private var favoritesRepository: FavoriteRepository = FavoriteRepository(application.applicationContext)
    override val inputs: IProfileViewModelInputs = this
    override val outputs: IProfileViewModelOutputs = this

    // inputs
    override val favoriteButtonClicked = PublishSubject.create<Unit>()

    override val trainer: Observable<Trainer>
    override val totalFavorites: Observable<Int>

    // endregion Variables

    // region Functions

    init {
        trainer = getCurrentTrainer()
        totalFavorites = getTotalPokemonFavorites()
    }

    private fun getCurrentTrainer(): Observable<Trainer> {
        return trainerRepository.getTrainer()
    }

    private fun getTotalPokemonFavorites(): Observable<Int> {
        return favoritesRepository.getTotalFavorites()
    }

    // endregion Functions
}