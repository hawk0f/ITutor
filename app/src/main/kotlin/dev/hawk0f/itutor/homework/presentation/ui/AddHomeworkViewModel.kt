package dev.hawk0f.itutor.homework.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.homework.domain.usecases.AddHomeworkUseCase
import dev.hawk0f.itutor.homework.domain.usecases.FetchHomeworksUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class AddHomeworkState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class AddHomeworkIntent {
    data class StudentClick(val studentId: Int) : AddHomeworkIntent()
    data class StudentDeleteClick(val studentId: Int) : AddHomeworkIntent()
    data object AddStudentClick : AddHomeworkIntent()
    data object FetchStudents : AddHomeworkIntent()
}

sealed class AddHomeworkEffect {
    data class NavigateToEditStudent(val studentId: Int) : AddHomeworkEffect()
    data object NavigateToAddStudent : AddHomeworkEffect()
    data class ShowMessage(val message: String) : AddHomeworkEffect()
}

@HiltViewModel
class AddHomeworkViewModel @Inject constructor(
    private val addHomeworkUseCase: AddHomeworkUseCase,
    private val fetchHomeworksUseCase: FetchHomeworksUseCase,
    val validateIsEmpty: ValidateIsEmpty
) : BaseViewModel<AddHomeworkState, AddHomeworkIntent, AddHomeworkEffect>() {
    //    private var lessonId = 0
//    private var studentId = 0
//
//    private val _homeworksState = MutableUIStateFlow<List<LessonStudentUI>>()
//    val homeworksState = _homeworksState.asStateFlow()
//
//    private val _addState = MutableUIStateFlow<Unit>()
//    val addState = _addState.asStateFlow()
//
//    fun fetchHomeworks() = fetchHomeworksUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_homeworksState) { list ->
//        list.map { it.toUi() }
//    }
//
//    fun addHomework(homework: String) = addHomeworkUseCase(studentId, lessonId, homework).collectNetworkRequest(_addState)
//
//    fun setLessonId(lessonId: Int)
//    {
//        this.lessonId = lessonId
//    }
//
//    fun setStudentId(studentId: Int)
//    {
//        this.studentId = studentId
//    }
    override val initialState: AddHomeworkState = AddHomeworkState()

    override suspend fun handleIntent(intent: AddHomeworkIntent) {
        when (intent) {
            else -> {}
        }
    }
}