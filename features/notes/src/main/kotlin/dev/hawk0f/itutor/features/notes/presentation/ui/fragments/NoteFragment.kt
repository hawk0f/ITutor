package dev.hawk0f.itutor.features.notes.presentation.ui.fragments

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.base.BaseHorizontalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.base.BaseVerticalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.features.notes.R
import dev.hawk0f.itutor.features.notes.databinding.FragmentNoteBinding
import dev.hawk0f.itutor.features.notes.presentation.ui.adapters.NoteAdapter
import dev.hawk0f.itutor.features.notes.presentation.ui.viewmodels.NoteViewModel
import dev.hawk0f.itutor.navigation.NoteFragmentDirections
import jp.wasabeef.recyclerview.animators.FadeInAnimator

@AndroidEntryPoint
class NoteFragment : BaseFragment<NoteViewModel, FragmentNoteBinding>(R.layout.fragment_note)
{
    override val viewModel: NoteViewModel by viewModels()
    override val binding: FragmentNoteBinding by viewBinding(FragmentNoteBinding::bind)

    private val noteAdapter = NoteAdapter({
        findNavController().navigateSafely(NoteFragmentDirections.actionNoteFragmentToEditNoteFragment(it))
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

            itemAnimator = FadeInAnimator()

            addItemDecoration(BaseHorizontalDividerItemDecoration(40))
            addItemDecoration(BaseVerticalDividerItemDecoration(30, 10))
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
            if (it.isEmpty())
            {
                noNotes.isVisible = true
                recyclerNotes.isVisible = false
            }
            else
            {
                noteAdapter.submitList(it)
            }
        })
    }

    private fun subscribeToDelete() = with(binding) {
        viewModel.deleteState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader, false)
        }, onSuccess = {
            fetchNotes()
        })
    }

    override fun setupListeners()
    {
        setupAddNoteButton()
    }

    private fun setupAddNoteButton() = with(binding) {
        btnAddNote.setOnClickListener {
            findNavController().navigateSafely(NoteFragmentDirections.actionNoteFragmentToAddNoteFragment())
        }
    }
}