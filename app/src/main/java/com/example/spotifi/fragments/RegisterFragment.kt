package com.example.spotifi.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.providers.implementations.AuthProviderImpl
import com.example.data.remote.providers.interfaces.AuthProvider
import com.example.domain.repositories.implementations.AuthRepositoryImpl
import com.example.domain.repositories.interfaces.AuthRepository
import com.example.domain.view_models.AuthViewModel
import com.example.domain.view_models.AuthViewModelFactory
import com.example.spotifi.R
import com.example.spotifi.di.App
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {
    private lateinit var loadingBar: ProgressDialog;
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewModelFactory: AuthViewModelFactory
    private lateinit var repository: AuthRepository
    private lateinit var provider: AuthProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoadingBar()

        provider = AuthProviderImpl()
        repository = AuthRepositoryImpl(provider, App.roomAppDatabase)
        viewModelFactory = AuthViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        viewModel.registerResponse.observe(viewLifecycleOwner) {
            loadingBar.cancel()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            loadingBar.cancel()
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }


        register_button.setOnClickListener {
            val username = register_username.text;
            val password = register_password.text;

            createNewAccount(
                username = username.toString(),
                password = password.toString(),
                view.context
            )
        }

        register_navigate_to_login.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
    }

    private fun setupLoadingBar() {
        loadingBar = ProgressDialog(view?.context);
        loadingBar.setTitle("Creating account...")
        loadingBar.setMessage("Please wait...")
        loadingBar.setCanceledOnTouchOutside(false);
    }

    private fun createNewAccount(username: String, password: String, context: Context) {
        when {
            username.isBlank() -> {
                Toast.makeText(context, "Please enter your username...", Toast.LENGTH_SHORT).show();
            }
            password.isBlank() -> {
                Toast.makeText(context, "Please enter your username...", Toast.LENGTH_SHORT).show();
            }
            else -> {
                val authForm = AuthForm(username, password)
                loadingBar.show()
                viewModel.register(authForm)
            }
        }
    }
}