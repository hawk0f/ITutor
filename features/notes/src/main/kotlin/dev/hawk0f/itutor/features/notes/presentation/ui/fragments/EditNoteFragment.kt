package dev.hawk0f.itutor.features.notes.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.R.string
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.notes.R
import dev.hawk0f.itutor.features.notes.databinding.FragmentEditNoteBinding
import dev.hawk0f.itutor.features.notes.presentation.ui.viewmodels.EditNoteViewModel
import dev.hawk0f.itutor.navigation.EditNoteFragmentArgs

@AndroidEntryPoint
class EditNoteFragment : BaseFragment<EditNoteViewModel, FragmentEditNoteBinding>(R.layout.fragment_edit_note)
{
    override val viewModel: EditNoteViewModel by viewModels()
    override val binding: FragmentEditNoteBinding by viewBinding(FragmentEditNoteBinding::bind)

    private var noteId = 0

    override fun initialize()
    {
        setupArguments()
        setupFields()
    }

    private fun setupArguments()
    {
        noteId = EditNoteFragmentArgs.fromBundle(requireArguments()).noteId
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
        fetchNote()
    }

    private fun fetchNote()
    {
        viewModel.getNoteById(noteId)
    }

    override fun setupSubscribers()
    {
        subscribeToNote()
        subscribeToUpdate()
    }

    private fun subscribeToNote() = with(binding) {
        viewModel.noteState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader)
        }, onSuccess = {
            viewModel.setNote(it)
            setupViewModel()
        })
    }

    private fun subscribeToUpdate() = with(binding) {
        viewModel.updateState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader, false)
        }, onSuccess = {
            showToastLong(string.success_note_updated)
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupEditNoteButtonListener()
    }

    private fun setupEditNoteButtonListener() = with(binding) {
        btnUpdateNote.setOnClickListener {
            if (textEd.fullText.isEmpty())
            {
                showToastLong(string.fill_note_text)
            }
            else
            {
                if (viewModel.isUpdateNeeded())
                {
                    viewModel.updateNote()
                }
                else
                {
                    findNavController().popBackStack()
                }
            }
        }
    }
}