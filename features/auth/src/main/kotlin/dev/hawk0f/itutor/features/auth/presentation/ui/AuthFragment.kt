package dev.hawk0f.itutor.features.auth.presentation.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.hideKeyboard
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.setupEmailValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupPasswordValidator
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.features.auth.R
import dev.hawk0f.itutor.features.auth.databinding.FragmentAuthBinding
import dev.hawk0f.itutor.navigation.AuthFragmentDirections

@AndroidEntryPoint
class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(R.layout.fragment_auth)
{
    override val viewModel: AuthViewModel by viewModels()
    override val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)

    override fun initialize()
    {
        setupFields()
        setupViewModel()
    }

    private fun setupFields() = with(binding)
    {
        viewModel.clearFields()
        emailLayout.setupEmailValidator()
        passwordLayout.setupPasswordValidator()
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun setupListeners()
    {
        setupAuthButtonListener()
        setupRegButtonListener()
    }

    private fun setupRegButtonListener() = with(binding) {
        btnGoToSignUp.setOnClickListener {
            findNavController().navigateSafely(AuthFragmentDirections.actionAuthFragmentToRegisterFragment())
        }
    }

    private fun setupAuthButtonListener() = with(binding)
    {
        btnSignIn.setOnClickListener {
            validateInputs(
                Pair(viewModel.validateEmail, emailLayout),
                Pair(viewModel.validatePassword, passwordLayout)
            ) {
                viewModel.authUser()
            }
        }
    }

    override fun setupSubscribers()
    {
        subscribeToAuth()
    }

    private fun subscribeToAuth() = with(binding) {
        viewModel.authState.collectAsUIState(state = {
            hideKeyboard()
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            CurrentUser.setUserId(it.id)
            showToastLong("Добро пожаловать, ${it.name}")
            findNavController().navigateSafely(AuthFragmentDirections.actionAuthFragmentToMainContentFragment())
        })
    }
}