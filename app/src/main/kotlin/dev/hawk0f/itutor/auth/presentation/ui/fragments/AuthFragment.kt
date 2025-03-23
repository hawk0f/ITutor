package dev.hawk0f.itutor.auth.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.hideKeyboard
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.auth.presentation.ui.AuthViewModel
import dev.hawk0f.itutor.databinding.FragmentAuthBinding

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

    private fun setupFields()
    {
        viewModel.clearFields()
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

    private fun setupAuthButtonListener() = with(binding) {
        btnSignIn.setOnClickListener {
            validateInputs(Pair(viewModel.validateEmail, emailLayout), Pair(viewModel.validatePassword, passwordLayout)) {
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
            viewModel.userDataPreferences.userId = it.id
            CurrentUser.setUserId(it.id)
            findNavController().navigateSafely(AuthFragmentDirections.actionAuthFragmentToMainContentFragment())
        })
    }
}