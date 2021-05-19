package com.example.pokeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.db.entities.Trainer
import com.example.pokeapp.repositories.TrainerRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface IProfileViewModelInputs {
    val favoriteButtonClicked: Observer<Unit>
}

interface IProfileViewModelOutputs {
    val trainer: Observable<Trainer>
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
    override val inputs: IProfileViewModelInputs = this
    override val outputs: IProfileViewModelOutputs = this

    // inputs
    override val favoriteButtonClicked = PublishSubject.create<Unit>()

    override val trainer: Observable<Trainer>

    // endregion Variables

    // region Functions

    init {
        trainer = getCurrentTrainer()
    }

    private fun getCurrentTrainer(): Observable<Trainer> {
        return trainerRepository.getTrainer()
    }

    // endregion Functions
}