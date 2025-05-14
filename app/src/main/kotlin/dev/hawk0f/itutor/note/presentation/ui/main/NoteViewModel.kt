package dev.hawk0f.itutor.note.presentation.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.note.domain.usecases.DeleteNoteUseCase
import dev.hawk0f.itutor.note.domain.usecases.FetchNotesUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class NotesState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class NotesIntent {
    data class StudentClick(val studentId: Int) : NotesIntent()
    data class StudentDeleteClick(val studentId: Int) : NotesIntent()
    data object AddStudentClick : NotesIntent()
    data object FetchStudents : NotesIntent()
}

sealed class NotesEffect {
    data class NavigateToEditStudent(val studentId: Int) : NotesEffect()
    data object NavigateToAddStudent : NotesEffect()
    data class ShowMessage(val message: String) : NotesEffect()
}

@HiltViewModel
class NoteViewModel @Inject constructor(private val fetchNotesUseCase: FetchNotesUseCase, private val deleteNoteUseCase: DeleteNoteUseCase) : BaseViewModel<NotesState, NotesIntent, NotesEffect>()
{
    //    private val _noteState = MutableUIStateFlow<List<NoteUI>>()
//    val noteState = _noteState.asStateFlow()
//
//    private val _deleteState = MutableUIStateFlow<Unit>()
//    val deleteState = _deleteState.asStateFlow()
//
//    fun fetchNotes() = fetchNotesUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_noteState) { list ->
//        list.map { it.toUi() }
//    }
//
//    fun deleteNote(noteId: Int) = deleteNoteUseCase(noteId).collectNetworkRequest(_deleteState)
    override val initialState: NotesState = NotesState()

    override suspend fun handleIntent(intent: NotesIntent) {
        when (intent) {
            else -> {}
        }
    }
}