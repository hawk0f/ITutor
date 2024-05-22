package dev.hawk0f.itutor.features.profile.presentation.ui.fragments

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.R.string
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.setupEmailValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupNameValidator
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.features.profile.R
import dev.hawk0f.itutor.features.profile.databinding.FragmentProfileBinding
import dev.hawk0f.itutor.features.profile.presentation.ui.viewmodels.ProfileViewModel
import dev.hawk0f.itutor.navigation.ProfileFragmentDirections
import ru.tinkoff.decoro.FormattedTextChangeListener
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile)
{
    override val viewModel: ProfileViewModel by viewModels()
    override val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun initialize() = with(binding) {
        nameLayout.setupNameValidator()
        surnameLayout.setupNameValidator()
        emailLayout.setupEmailValidator()
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun setupRequests()
    {
        fetchUser()
    }

    private fun fetchUser()
    {
        viewModel.getUserById()
    }

    override fun setupSubscribers()
    {
        subscribeToUpdate()
        subscribeToUser()
    }

    private fun subscribeToUpdate() = with(binding)
    {
        viewModel.updateState.collectAsUIState {
            showToastLong(string.success_data_updated)
            viewModel.onUpdateSuccess()
            btnUpdateUser.isEnabled = false
        }
    }

    private fun subscribeToUser()
    {
        viewModel.userState.collectAsUIState {
            viewModel.setUser(it)
            setupPhoneNumberTextChange()
            setupViewModel()
        }
    }

    override fun setupListeners()
    {
        setupUpdateButtonListener()
        setupLogoutButtonListener()

        setupNameTextChange()
        setupSurnameTextChange()
        setupEmailTextChange()
    }

    private fun setupUpdateButtonListener() = with(binding) {
        btnUpdateUser.setOnClickListener {
            validateInputs(Pair(viewModel.validateName, nameLayout), Pair(viewModel.validateName, surnameLayout), Pair(viewModel.validateEmail, emailLayout)) {
                if (phoneEditText.fullText.isEmpty() || phoneEditText.fullText.length == 16)
                {
                    phoneLayout.error = null
                    viewModel.updateUser()
                }
                else
                {
                    phoneLayout.error = getString(string.complete_your_phone_number_or_delete)
                }
            }
        }
    }

    private fun setupLogoutButtonListener() = with(binding) {
        btnLogOut.setOnClickListener {
            viewModel.userDataPreferences.userId = 0
            CurrentUser.setUserId(0)
            findNavController().navigateSafely(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())
        }
    }

    private fun setupNameTextChange() = with(binding) {
        nameEditText.doAfterTextChanged { _ ->
            btnUpdateUser.isEnabled = viewModel.isUpdateNeeded()
        }
    }

    private fun setupSurnameTextChange() = with(binding) {
        surnameEditText.doAfterTextChanged { _ ->
            btnUpdateUser.isEnabled = viewModel.isUpdateNeeded()
        }
    }

    private fun setupEmailTextChange() = with(binding) {
        emailEditText.doAfterTextChanged { _ ->
            btnUpdateUser.isEnabled = viewModel.isUpdateNeeded()
        }
    }

    private fun setupPhoneNumberTextChange() = with(binding) {

        val slots = UnderscoreDigitSlotsParser().parseSlots("+7 ___ ___-__-__")
        val mask: MaskImpl = MaskImpl.createTerminated(slots)
        mask.isForbidInputWhenFilled = true

        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)

        formatWatcher.setCallback(object : FormattedTextChangeListener
        {
            override fun beforeFormatting(p0: String?, p1: String?): Boolean = false

            override fun onTextFormatted(p0: FormatWatcher?, p1: String?)
            {
                btnUpdateUser.isEnabled = viewModel.isUpdateNeeded()
            }
        })
        formatWatcher.installOn(phoneEditText)

        phoneEditText.setOnFocusChangeListener { _, hasFocus ->
            when
            {
                !hasFocus && phoneEditText.fullText.length < 16 && phoneEditText.fullText.isNotEmpty() ->
                {
                    phoneLayout.error = getString(string.complete_your_phone_number_or_delete)
                }

                hasFocus ->
                {
                    phoneLayout.error = null
                }

                else ->
                {
                    phoneLayout.error = null
                }
            }
        }
    }
}