package dev.hawk0f.itutor.features.notes.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.NoteDTO
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.NoteUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.notes.domain.usecases.GetNoteByIdUseCase
import dev.hawk0f.itutor.features.notes.domain.usecases.UpdateNoteUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val updateNoteUseCase: UpdateNoteUseCase, private val getNoteByIdUseCase: GetNoteByIdUseCase) : BaseViewModel()
{
    private var oldText = ""
    private var oldHeader = ""
    private var id = 0
    var text = ""
    var header = ""
    private var userId = 0

    private val _noteState = MutableUIStateFlow<NoteUI>()
    val noteState = _noteState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    fun setNote(note: NoteUI)
    {
        oldHeader = note.fullHeader
        oldText = note.fullText
        id = note.id
        header = note.fullHeader
        text = note.fullText
        userId = note.userId
    }

    fun getNoteById(noteId: Int) = getNoteByIdUseCase(noteId).collectNetworkRequestWithMapping(_noteState) {
        it.toUi()
    }

    fun isUpdateNeeded() = oldText != text || oldHeader != header

    fun updateNote() = updateNoteUseCase(
        NoteDTO(
            id = id,
            header = header,
            text = text,
            userId = userId
        )).collectNetworkRequest(_updateState)

    fun clearFields()
    {
        header = ""
        text = ""
    }
}