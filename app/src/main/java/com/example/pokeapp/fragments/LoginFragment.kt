package com.example.pokeapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokeapp.R
import com.example.pokeapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    //region Variables

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //endregion Variables

    //region Functions

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

        setupListeners()

        binding.loginButton.setOnClickListener{

            // check if the login form is valid before to proceed
            if (isValidForm())
            {
                // make the transition between the login to the main fragment
                findNavController().navigate(R.id.action_loginFragment_to_pokemonListFragment)
            }
        }
    }

    private fun setupListeners() {
        binding.trainerEditText.addTextChangedListener(TextFieldValidation(binding.trainerEditText))
    }

    private fun isValidForm(): Boolean = isValidTrainerName()

    private fun isValidTrainerName(): Boolean {

        if (binding.trainerEditText.text.toString().trim().isEmpty()) {
            binding.trainerInputLayout.error = getString(R.string.required_field_text)
            binding.trainerEditText.requestFocus()
            return false
        }
        else {
            binding.trainerInputLayout.isErrorEnabled = false
        }

        return true
    }

    //endregion Functions

    //region TextFieldValidation

    inner class TextFieldValidation(private val view:View) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            when (view.id) {
                R.id.trainerEditText -> {
                    isValidTrainerName()
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //endregion TextFieldValidation
}