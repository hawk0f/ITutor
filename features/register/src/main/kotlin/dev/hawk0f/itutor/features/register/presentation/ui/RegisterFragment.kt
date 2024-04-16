package dev.hawk0f.itutor.features.register.presentation.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.register.R
import dev.hawk0f.itutor.features.register.databinding.FragmentRegisterBinding
import dev.hawk0f.itutor.navigation.R.id.action_global_authFragment

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
        lifecycleOwner = this@RegisterFragment.viewLifecycleOwner
    }

    override fun setupListeners()
    {
        setupAuthButtonListener()
    }

    private fun setupAuthButtonListener() = with(binding) {
        btnGoToSignIn.setOnClickListener {
            findNavController().navigate(action_global_authFragment)
        }
    }

    override fun setupSubscribers()
    {
        subscribeToReg()
    }

    private fun subscribeToReg() = with(binding) {
        viewModel.regState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            showToastLong("Добро пожаловать, ${it.name}")
            //TODO navigate to mainContent
            //findNavController().navigate()
        })
    }
}