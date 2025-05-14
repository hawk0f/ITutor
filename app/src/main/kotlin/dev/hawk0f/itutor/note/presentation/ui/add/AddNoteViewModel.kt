package dev.hawk0f.itutor.note.presentation.ui.add

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.note.data.models.NoteDTO
import dev.hawk0f.itutor.note.domain.usecases.AddNoteUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class AddNoteState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class AddNoteIntent {
    data class StudentClick(val studentId: Int) : AddNoteIntent()
    data class StudentDeleteClick(val studentId: Int) : AddNoteIntent()
    data object AddStudentClick : AddNoteIntent()
    data object FetchStudents : AddNoteIntent()
}

sealed class AddNoteEffect {
    data class NavigateToEditStudent(val studentId: Int) : AddNoteEffect()
    data object NavigateToAddStudent : AddNoteEffect()
    data class ShowMessage(val message: String) : AddNoteEffect()
}

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val addNoteUseCase: AddNoteUseCase) :
    BaseViewModel<AddNoteState, AddNoteIntent, AddNoteEffect>() {
    //    var header = ""
//    var text = ""
//
//    private val _addState = MutableUIStateFlow<Unit>()
//    val addState = _addState.asStateFlow()
//
//    fun addNote() = addNoteUseCase(
//        NoteDTO(
//            0,
//            header,
//            text,
//            CurrentUser.getUserId()
//        )
//    ).collectNetworkRequest(_addState)
//
//    fun clearFields()
//    {
//        header = ""
//        text = ""
//    }
    override val initialState: AddNoteState = AddNoteState()

    override suspend fun handleIntent(intent: AddNoteIntent) {
        when (intent) {
            else -> {}
        }
    }
}