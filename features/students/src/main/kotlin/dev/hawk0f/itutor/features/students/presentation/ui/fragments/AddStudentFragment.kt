package dev.hawk0f.itutor.features.students.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.students.R
import dev.hawk0f.itutor.features.students.databinding.FragmentAddStudentBinding
import dev.hawk0f.itutor.features.students.presentation.ui.viewmodels.AddStudentViewModel
import ru.tinkoff.decoro.FormattedTextChangeListener
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
    }

    private fun setupFields()
    {
        viewModel.clearFields()
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
            showToastLong("Успешное добавление")
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupPhoneNumberTextChange()
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