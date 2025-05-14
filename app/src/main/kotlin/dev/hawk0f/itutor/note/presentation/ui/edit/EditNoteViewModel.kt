package dev.hawk0f.itutor.note.presentation.ui.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.note.domain.usecases.GetNoteByIdUseCase
import dev.hawk0f.itutor.note.domain.usecases.UpdateNoteUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class EditNoteState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class EditNoteIntent {
    data class StudentClick(val studentId: Int) : EditNoteIntent()
    data class StudentDeleteClick(val studentId: Int) : EditNoteIntent()
    data object AddStudentClick : EditNoteIntent()
    data object FetchStudents : EditNoteIntent()
}

sealed class EditNoteEffect {
    data class NavigateToEditStudent(val studentId: Int) : EditNoteEffect()
    data object NavigateToAddStudent : EditNoteEffect()
    data class ShowMessage(val message: String) : EditNoteEffect()
}

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : BaseViewModel<EditNoteState, EditNoteIntent, EditNoteEffect>() {
    //    private var oldText = ""
//    private var oldHeader = ""
//    private var id = 0
//    var text = ""
//    var header = ""
//    private var userId = 0
//
//    private val _noteState = MutableUIStateFlow<NoteUI>()
//    val noteState = _noteState.asStateFlow()
//
//    private val _updateState = MutableUIStateFlow<Unit>()
//    val updateState = _updateState.asStateFlow()
//
//    fun setNote(note: NoteUI)
//    {
//        oldHeader = note.fullHeader
//        oldText = note.fullText
//        id = note.id
//        header = note.fullHeader
//        text = note.fullText
//        userId = note.userId
//    }
//
//    fun getNoteById(noteId: Int) = getNoteByIdUseCase(noteId).collectNetworkRequestWithMapping(_noteState) {
//        it.toUi()
//    }
//
//    fun isUpdateNeeded() = oldText != text || oldHeader != header
//
//    fun updateNote() = updateNoteUseCase(
//        NoteDTO(
//            id = id,
//            header = header,
//            text = text,
//            userId = userId
//        )
//    ).collectNetworkRequest(_updateState)
//
//    fun clearFields()
//    {
//        header = ""
//        text = ""
//    }
    override val initialState: EditNoteState = EditNoteState()

    override suspend fun handleIntent(intent: EditNoteIntent) {
        when (intent) {
            else -> {}
        }
    }
}