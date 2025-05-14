package dev.hawk0f.itutor.lesson.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.lesson.domain.usecases.DeleteLessonUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.FetchLessonsUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class LessonsState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class LessonsIntent {
    data class StudentClick(val studentId: Int) : AddLessonIntent()
    data class StudentDeleteClick(val studentId: Int) : AddLessonIntent()
    data object AddStudentClick : AddLessonIntent()
    data object FetchStudents : AddLessonIntent()
}

sealed class LessonsEffect {
    data class NavigateToEditStudent(val studentId: Int) : AddLessonEffect()
    data object NavigateToAddStudent : AddLessonEffect()
    data class ShowMessage(val message: String) : AddLessonEffect()
}

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val fetchLessonsUseCase: FetchLessonsUseCase,
    private val deleteLessonUseCase: DeleteLessonUseCase
) : BaseViewModel<LessonsState, AddLessonIntent, AddLessonEffect>() {
    //    private val _lessonState = MutableUIStateFlow<List<DateLessonsUI>>()
//    val lessonState = _lessonState.asStateFlow()
//
//    private val _deleteState = MutableUIStateFlow<Unit>()
//    val deleteState = _deleteState.asStateFlow()
//
//    fun fetchLessons() = fetchLessonsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonState) { list ->
//        val dateLessonsList = ArrayList<DateLessonsUI>()
//        list.forEach { lesson ->
//            val lessonUI = lesson.toUi()
//            val currentDateLessons = dateLessonsList.firstOrNull { it.date == lessonUI.parsedDate }
//            if (currentDateLessons != null)
//            {
//                currentDateLessons.lessons.add(lessonUI)
//            }
//            else
//            {
//                val newDateLessons = DateLessonsUI(lessonUI.parsedDate, lessons = ArrayList<LessonUI>().apply {
//                    add(lessonUI)
//                })
//                dateLessonsList.add(newDateLessons)
//            }
//        }
//        dateLessonsList
//    }
//
//    fun deleteLesson(lessonId: Int) = deleteLessonUseCase(lessonId).collectNetworkRequest(_deleteState)
    override val initialState: LessonsState = LessonsState()

    override suspend fun handleIntent(intent: AddLessonIntent) {
        when (intent) {
            else -> {}
        }
    }
}