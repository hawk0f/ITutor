package dev.hawk0f.itutor.register.presentation.ui.fragments

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
import dev.hawk0f.itutor.databinding.FragmentRegisterBinding
import dev.hawk0f.itutor.register.presentation.ui.viewmodels.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(R.layout.fragment_register)
{
    override val viewModel: RegisterViewModel by viewModels()
    override val binding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)

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
        setupRegButtonListener()
        setupAuthButtonListener()
    }

    private fun setupAuthButtonListener() = with(binding) {
        btnGoToSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRegButtonListener() = with(binding) {
        btnSignUp.setOnClickListener {
            validateInputs(Pair(viewModel.validateName, nameLayout), Pair(viewModel.validateName, surnameLayout), Pair(viewModel.validateEmail, emailLayout), Pair(viewModel.validatePassword, passwordLayout)) {
                viewModel.registerUser()
            }
        }
    }

    override fun setupSubscribers()
    {
        subscribeToReg()
    }

    private fun subscribeToReg() = with(binding) {
        viewModel.regState.collectAsUIState(state = {
            hideKeyboard()
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            viewModel.userDataPreferences.userId = it.id
            CurrentUser.setUserId(it.id)
            findNavController().navigateSafely(RegisterFragmentDirections.actionRegisterFragmentToMainContentFragment())
        })
    }
}