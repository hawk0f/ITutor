package dev.hawk0f.itutor.note.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.databinding.FragmentAddNoteBinding
import dev.hawk0f.itutor.note.presentation.ui.viewmodels.AddNoteViewModel

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<AddNoteViewModel, FragmentAddNoteBinding>(R.layout.fragment_add_note)
{
    override val viewModel: AddNoteViewModel by viewModels()
    override val binding: FragmentAddNoteBinding by viewBinding(FragmentAddNoteBinding::bind)

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
            it.setupViewVisibilityLinear(group, loader, false)
        }, onSuccess = {
            showToastLong(dev.hawk0f.itutor.core.R.string.success_note_added)
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupAddNoteButtonListener()
    }

    private fun setupAddNoteButtonListener() = with(binding) {
        btnAddNote.setOnClickListener {
            if (textEd.fullText.isEmpty())
            {
                showToastLong(dev.hawk0f.itutor.core.R.string.fill_note_text)
            }
            else
            {
                viewModel.addNote()
            }
        }
    }
}