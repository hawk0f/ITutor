package dev.hawk0f.itutor.features.notes.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.features.notes.R
import dev.hawk0f.itutor.features.notes.databinding.FragmentNoteBinding
import dev.hawk0f.itutor.features.notes.presentation.ui.adapters.NoteAdapter
import dev.hawk0f.itutor.features.notes.presentation.ui.viewmodels.NoteViewModel

@AndroidEntryPoint
class NoteFragment : BaseFragment<NoteViewModel, FragmentNoteBinding>(R.layout.fragment_note)
{
    override val viewModel: NoteViewModel by viewModels()
    override val binding: FragmentNoteBinding by viewBinding(FragmentNoteBinding::bind)

    private val noteAdapter = NoteAdapter({
        //TODO Navigate to EditNoteFragment
    }, {
        viewModel.deleteNote(it)
    })

    override fun initialize()
    {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerNotes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    override fun setupRequests()
    {
        fetchNotes()
    }

    private fun fetchNotes()
    {
        viewModel.fetchNotes()
    }

    override fun setupSubscribers()
    {
        subscribeToNotes()
        subscribeToDelete()
    }

    private fun subscribeToNotes() = with(binding) {
        viewModel.noteState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader)
        }, onSuccess = {
            noteAdapter.submitList(it)
        })
    }

    private fun subscribeToDelete() = with(binding) {
        viewModel.deleteState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader, false)
        }, onSuccess = {
            fetchNotes()
        })
    }

    override fun setupListeners() = with(binding) {
        btnAddNote.setOnClickListener {
            //TODO navigate to AddNoteFragment
        }
    }
}