package dev.hawk0f.itutor.student.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.setupAgeValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupNameValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupPhoneValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupPriceValidator
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.databinding.FragmentAddStudentBinding
import dev.hawk0f.itutor.student.presentation.ui.viewmodels.AddStudentViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

@AndroidEntryPoint
class AddStudentFragment : BaseFragment<AddStudentViewModel, FragmentAddStudentBinding>(R.layout.fragment_add_student)
{
    override val viewModel: AddStudentViewModel by viewModels()
    override val binding: FragmentAddStudentBinding by viewBinding(FragmentAddStudentBinding::bind)

    override fun initialize()
    {
        setupFields()
        setupViewModel()
        initializeValidators()
    }

    private fun setupFields()
    {
        viewModel.clearFields()
    }

    private fun initializeValidators() = with(binding)
    {
        nameLayout.setupNameValidator()
        surnameLayout.setupNameValidator()
        ageLayout.setupAgeValidator()
        phoneNumberLayout.setupPhoneValidator()
        singlePriceLayout.setupPriceValidator()
        groupPriceLayout.setupPriceValidator()
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun setupSubscribers()
    {
        subscribeToAdd()
    }

    private fun subscribeToAdd() = with(binding) {
        viewModel.addState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            showToastLong(dev.hawk0f.itutor.core.R.string.success_student_added)
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupAddStudentButtonListener()
        setupPhoneNumberTextChange()
    }

    private fun setupAddStudentButtonListener() = with(binding)
    {
        btnAddStudent.setOnClickListener {
            validateInputs(
                Pair(viewModel.validateName, nameLayout),
                Pair(viewModel.validateName, surnameLayout),
                Pair(viewModel.validateAge, ageLayout),
                Pair(viewModel.validatePhone, phoneNumberLayout),
                Pair(viewModel.validatePrice, singlePriceLayout),
                Pair(viewModel.validatePrice, groupPriceLayout)
            ) {
                viewModel.addStudent()
            }
        }
    }

    private fun setupPhoneNumberTextChange() = with(binding)
    {
        val slots = UnderscoreDigitSlotsParser().parseSlots("+7 ___ ___-__-__")
        val mask: MaskImpl = MaskImpl.createTerminated(slots)
        mask.isForbidInputWhenFilled = true
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(phoneNumberEditText)
    }
}