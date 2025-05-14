package dev.hawk0f.itutor.lesson.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.lesson.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class StudentBottomSheetState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class StudentBottomSheetIntent {
    data class StudentClick(val studentId: Int) : AddLessonIntent()
    data class StudentDeleteClick(val studentId: Int) : AddLessonIntent()
    data object AddStudentClick : AddLessonIntent()
    data object FetchStudents : AddLessonIntent()
}

sealed class StudentBottomSheetEffect {
    data class NavigateToEditStudent(val studentId: Int) : AddLessonEffect()
    data object NavigateToAddStudent : AddLessonEffect()
    data class ShowMessage(val message: String) : AddLessonEffect()
}

@HiltViewModel
class StudentBottomSheetViewModel @Inject constructor(private val getLessonStudentsUseCase: FetchLessonStudentsUseCase) :
    BaseViewModel<StudentBottomSheetState, AddLessonIntent, AddLessonEffect>() {
    //    private val _lessonStudentsState = MutableUIStateFlow<List<StudentInLessonUI>>()
//    val lessonStudentsState = _lessonStudentsState.asStateFlow()
//
//    fun getLessonStudents(studentsIds: MutableList<Int>) = getLessonStudentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
//        list.map { it.toUI(studentsIds.count()) }
//    }
    override val initialState: StudentBottomSheetState = StudentBottomSheetState()

    override suspend fun handleIntent(intent: AddLessonIntent) {
        when (intent) {
            else -> {}
        }
    }
}