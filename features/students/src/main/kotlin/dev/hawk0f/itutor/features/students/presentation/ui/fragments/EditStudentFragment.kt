package dev.hawk0f.itutor.features.students.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.R.*
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.setupAgeValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupNameValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupPhoneValidator
import dev.hawk0f.itutor.core.presentation.extensions.setupPriceValidator
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.features.students.R
import dev.hawk0f.itutor.features.students.databinding.FragmentEditStudentBinding
import dev.hawk0f.itutor.features.students.presentation.ui.viewmodels.EditStudentViewModel
import dev.hawk0f.itutor.navigation.EditStudentFragmentArgs
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

@AndroidEntryPoint
class EditStudentFragment : BaseFragment<EditStudentViewModel, FragmentEditStudentBinding>(R.layout.fragment_edit_student)
{
    override val viewModel: EditStudentViewModel by viewModels()
    override val binding: FragmentEditStudentBinding by viewBinding(FragmentEditStudentBinding::bind)

    private var studentId: Int = 0

    override fun initialize()
    {
        setupArguments()
        setupFields()
        initializeValidators()
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

    private fun setupArguments()
    {
        studentId = EditStudentFragmentArgs.fromBundle(requireArguments()).studentId
    }

    private fun setupFields()
    {
        viewModel.clearFields()
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun setupRequests()
    {
        fetchStudent()
    }

    private fun fetchStudent()
    {
        viewModel.getStudentById(studentId)
    }

    override fun setupSubscribers()
    {
        subscribeToStudent()
        subscribeToUpdate()
    }

    private fun subscribeToStudent() = with(binding) {
        viewModel.studentState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader)
        }, onSuccess = {
            viewModel.setStudent(it)
            setupViewModel()
        })
    }

    private fun subscribeToUpdate() = with(binding) {
        viewModel.updateState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader,false)
        }, onSuccess = {
            showToastLong(string.success_student_updated)
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupEditStudentButtonListener()
        setupPhoneNumberTextChange()
    }

    private fun setupEditStudentButtonListener() = with(binding)
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
                if (viewModel.isUpdateNeeded())
                {
                    viewModel.updateStudent()
                }
                else
                {
                    findNavController().popBackStack()
                }
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