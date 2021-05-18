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
    val name: Observer<String>
    val email: Observer<String>
    val gender: Observer<String>
    val loginButtonClicked: Observer<Unit>
}

interface ILoginViewModelOutputs {
    val isButtonEnabled: Observable<Boolean>
    val trainerNameError: Observable<Boolean>
    val trainerEmailError: Observable<Boolean>
    val trainerCreated: Observable<Boolean>
    val isTrainerSignedIn: Observable<Boolean>
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
    override val name = BehaviorSubject.create<String>()
    override val email = BehaviorSubject.create<String>()
    override val gender = BehaviorSubject.create<String>()
    override val loginButtonClicked = PublishSubject.create<Unit>()

    // outputs
    override val isButtonEnabled: Observable<Boolean> = Observable.combineLatest(name, email, { n, e -> n.isNotEmpty() && e.isNotEmpty() })
    override val trainerNameError: Observable<Boolean> = name.map { it.isEmpty() }
    override val trainerEmailError: Observable<Boolean> = email.map { it.isEmpty() }
    override val trainerCreated: Observable<Boolean>
    override val isTrainerSignedIn: Observable<Boolean>

    // endregion Variables

    // region Functions

    init {
        trainerCreated = loginButtonClicked
            .withLatestFrom(name, email, gender, { _, n, e, g -> Trainer(name = n, email = e, gender = g) })
            .doOnNext { createNewTrainer( it ) }
            .map { true }

        isTrainerSignedIn = hasValidTrainer().map { it > 0 }
    }

    private fun createNewTrainer(trainer: Trainer) {
        viewModelScope.launch(Dispatchers.IO) {
            trainerRepository.insertTrainer(trainer)
        }
    }

    private fun hasValidTrainer(): Observable<Int> {
        return trainerRepository.getTotalTrainers()
    }

    // endregion Functions
}