package dev.hawk0f.features.auth.presentation.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.hawk0f.core.presentation.base.BaseFragment
import dev.hawk0f.core.presentation.extensions.showToastLong
import dev.hawk0f.features.auth.R
import dev.hawk0f.features.auth.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(R.layout.fragment_auth)
{
    override val viewModel: AuthViewModel by viewModels()
    override val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)

    override fun initialize()
    {
        setupFields()
    }

    private fun setupFields()
    {
        viewModel.clearFields()
    }

    override fun setupListeners() = with(binding)
    {
        btnGoToSignUp.setOnClickListener {
        //TODO navigate to register
        //findNavController().navigate()
        }
    }

    override fun setupSubscribers()
    {
        subscribeToAuth()
    }

    private fun subscribeToAuth() = with(binding)
    {
        viewModel.authState.collectAsUIState(
            state = {
                it.setupViewVisibility(group, loader)
            },
            onSuccess = {
                showToastLong("Добро пожаловать, ${it.email}")
                //TODO navigate to mainContent
            })
    }
}