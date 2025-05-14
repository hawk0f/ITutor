package dev.hawk0f.itutor.homework.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.homework.domain.usecases.FetchHomeworksUseCase
import dev.hawk0f.itutor.homework.domain.usecases.UpdateHomeworkStatusUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class HomeworkState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class HomeworkIntent {
    data class StudentClick(val studentId: Int) : HomeworkIntent()
    data class StudentDeleteClick(val studentId: Int) : HomeworkIntent()
    data object AddStudentClick : HomeworkIntent()
    data object FetchStudents : HomeworkIntent()
}

sealed class HomeworkEffect {
    data class NavigateToEditStudent(val studentId: Int) : HomeworkEffect()
    data object NavigateToAddStudent : HomeworkEffect()
    data class ShowMessage(val message: String) : HomeworkEffect()
}

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val fetchLessonStudentsUseCase: FetchHomeworksUseCase,
    private val updateHomeworkStatusUseCase: UpdateHomeworkStatusUseCase
) : BaseViewModel<HomeworkState, HomeworkIntent, HomeworkEffect>() {
    //    private val _lessonStudentsState = MutableUIStateFlow<List<StudentHomeworksUI>>()
//    val lessonStudentsState = _lessonStudentsState.asStateFlow()
//
//    private val _updateState = MutableUIStateFlow<Unit>()
//    val updateState = _updateState.asStateFlow()
//
//    private val allLessonStudents = ArrayList<LessonStudentUI>()
//
//    fun fetchLessonStudents() = fetchLessonStudentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
//        allLessonStudents.clear()
//        allLessonStudents.addAll(list.map { it.toUi() })
//
//        val studentHomeworksList = ArrayList<StudentHomeworksUI>()
//        allLessonStudents.forEach { lessonStudentUi ->
//            if (lessonStudentUi.fullHomework.isNotEmpty())
//            {
//                val currentStudentHomeworks =
//                    studentHomeworksList.firstOrNull { it.studentName == lessonStudentUi.studentName }
//                if (currentStudentHomeworks != null)
//                {
//                    currentStudentHomeworks.homeworks.add(lessonStudentUi)
//                }
//                else
//                {
//                    val newStudentHomeworks =
//                        StudentHomeworksUI(lessonStudentUi.studentName, homeworks = ArrayList<LessonStudentUI>().apply {
//                            add(lessonStudentUi)
//                        })
//                    studentHomeworksList.add(newStudentHomeworks)
//                }
//            }
//        }
//        studentHomeworksList
//    }
//
//    fun updateHomeworkStatus(studentId: Int, lessonId: Int, isHomeworkDone: Boolean) = updateHomeworkStatusUseCase(studentId, lessonId, isHomeworkDone).collectNetworkRequest(_updateState)
    override val initialState: HomeworkState = HomeworkState()

    override suspend fun handleIntent(intent: HomeworkIntent) {
        when (intent) {
            else -> {}
        }
    }
}