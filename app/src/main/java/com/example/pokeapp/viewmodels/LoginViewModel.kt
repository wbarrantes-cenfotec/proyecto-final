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

interface ILoginViewModelInputs {
    val trainerName: Observer<String>
    val loginButtonClicked: Observer<Unit>
}

interface ILoginViewModelOutputs {
    val isButtonEnabled: Observable<Boolean>
    val trainerNameError: Observable<Boolean>
    val isTrainerSignedIn: Observable<Boolean>
    val trainerCreated: Observable<Boolean>
}

interface ILoginViewModelType {
    val inputs: ILoginViewModelInputs
    val outputs: ILoginViewModelOutputs
}

class LoginViewModel(application: Application) : AndroidViewModel(application),
    ILoginViewModelInputs,
    ILoginViewModelOutputs,
    ILoginViewModelType {

    // region Variables

    private var trainerRepository: TrainerRepository = TrainerRepository(application.applicationContext)
    override val inputs: ILoginViewModelInputs = this
    override val outputs: ILoginViewModelOutputs = this

    // inputs
    override val trainerName: BehaviorSubject<String> = BehaviorSubject.create<String>()
    override val loginButtonClicked: PublishSubject<Unit> = PublishSubject.create<Unit>()

    // outputs
    override val isButtonEnabled: Observable<Boolean>
    override val trainerNameError: Observable<Boolean>
    override val isTrainerSignedIn: Observable<Boolean>
    override val trainerCreated: Observable<Boolean>

    // endregion Variables

    // region Functions

    init {
        isButtonEnabled = trainerName.map { it.isNotEmpty() }

        trainerNameError = trainerName.map { it.isEmpty() }

        trainerCreated = loginButtonClicked
            .withLatestFrom(trainerName, { e, n -> Trainer(n) })
            .doOnNext { createNewTrainer( it ) }
            .map { true }

        isTrainerSignedIn = hasValidTrainer().map { it > 0 }
    }

    private fun createNewTrainer(trainer: Trainer) {
        viewModelScope.launch(Dispatchers.IO) {
            trainerRepository.insertTrainer(trainer)
        }
    }

    fun hasValidTrainer(): Observable<Int> {
        return trainerRepository.getTotalTrainers()
    }

    // endregion Functions
}