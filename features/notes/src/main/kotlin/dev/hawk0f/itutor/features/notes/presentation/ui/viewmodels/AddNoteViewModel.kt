package dev.hawk0f.itutor.features.notes.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.NoteDTO
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.features.notes.domain.usecases.AddNoteUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val addNoteUseCase: AddNoteUseCase) : BaseViewModel()
{
    var header = ""
    var text = ""

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    fun addNote() = addNoteUseCase(
        NoteDTO(
            0,
            header,
            text,
            CurrentUser.getUserId()
        )).collectNetworkRequest(_addState)

    fun clearFields()
    {
        header = ""
        text = ""
    }
}