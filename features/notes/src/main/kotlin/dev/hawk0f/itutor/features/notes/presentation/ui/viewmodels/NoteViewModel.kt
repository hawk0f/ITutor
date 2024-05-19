package dev.hawk0f.itutor.features.notes.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.NoteUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.notes.domain.usecases.DeleteNoteUseCase
import dev.hawk0f.itutor.features.notes.domain.usecases.FetchNotesUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val fetchNotesUseCase: FetchNotesUseCase, private val deleteNoteUseCase: DeleteNoteUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _noteState = MutableUIStateFlow<List<NoteUI>>()
    val noteState = _noteState.asStateFlow()

    private val _deleteState = MutableUIStateFlow<Unit>()
    val deleteState = _deleteState.asStateFlow()

    fun fetchNotes() = fetchNotesUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_noteState) { list ->
        list.map { it.toUi() }
    }

    fun deleteNote(noteId: Int) = deleteNoteUseCase(noteId).collectNetworkRequest(_deleteState)
}