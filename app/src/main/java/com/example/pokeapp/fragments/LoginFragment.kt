package com.example.pokeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pokeapp.R
import com.example.pokeapp.databinding.FragmentLoginBinding
import com.example.pokeapp.viewmodels.ILoginViewModelType
import com.example.pokeapp.viewmodels.LoginViewModel
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class LoginFragment : Fragment(R.layout.fragment_login) {

    //region Variables

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ILoginViewModelType

    private val disposables = CompositeDisposable()

    //endregion Variables

    //region Functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize the view model
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // to update the trainer name
        disposables.add(
            RxTextView.textChanges(binding.trainerEditText)
                .skipInitialValue()
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.name.onNext(it)
                }
        )

        // to update the trainer email
        disposables.add(
            RxTextView.textChanges(binding.emailEditText)
                .skipInitialValue()
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.email.onNext(it)
                }
        )

        disposables.add(
            RxRadioGroup.checkedChanges(binding.radioGroup)
                .map {
                    if (it == binding.menRadioButton.id)
                        getString(R.string.men_text)
                    else
                        getString(R.string.female_text)
                }
                .subscribe {
                    viewModel.inputs.gender.onNext(it)
                }
        )

        // to show the error message on the trainer input text if the field is empty
        disposables.add(
            viewModel.outputs.trainerNameError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.trainerEditText.error = if (it) getString(R.string.required_field_text) else null
                }
        )

        // to show the error message on the email input text if the field is empty
        disposables.add(
            viewModel.outputs.trainerEmailError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.emailEditText.error = if (it) getString(R.string.required_field_text) else null
                }
        )

        // to enable/disable the login button
        disposables.add(
            viewModel.outputs.isButtonEnabled
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.loginButton.isEnabled = it
                }
        )

        // execute the logic on the login button click event
        disposables.add(
            RxView.clicks(binding.loginButton)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.inputs.loginButtonClicked.onNext(Unit)
                }
        )

        // when the trainer is created on the DB, then move to the next fragment
        disposables.add(
            viewModel.outputs.trainerCreated
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it) {
                            // make the transition between fragments
                            this.goToMainFragment()
                        }
                    },
                    {
                        print(it.localizedMessage)
                    }
                )
        )

        // if we already have a signed trainer on the DB, then move to the next fragment
        disposables.add(
            viewModel.outputs.isTrainerSignedIn
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it) {
                            // make the transition between fragments
                            this.goToMainFragment()
                        }
                    },
                    {
                        print(it.localizedMessage)
                    }
                )
        )
    }

    private fun goToMainFragment() {
        // make the transition between the login to the main fragment
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }

    //endregion Functions
}