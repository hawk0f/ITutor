package dev.hawk0f.itutor.homework.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.homework.domain.usecases.FetchHomeworksUseCase
import dev.hawk0f.itutor.homework.domain.usecases.UpdateHomeworkUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class EditHomeworkState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class EditHomeworkIntent {
    data class StudentClick(val studentId: Int) : EditHomeworkIntent()
    data class StudentDeleteClick(val studentId: Int) : EditHomeworkIntent()
    data object AddStudentClick : EditHomeworkIntent()
    data object FetchStudents : EditHomeworkIntent()
}

sealed class EditHomeworkEffect {
    data class NavigateToEditStudent(val studentId: Int) : EditHomeworkEffect()
    data object NavigateToAddStudent : EditHomeworkEffect()
    data class ShowMessage(val message: String) : EditHomeworkEffect()
}

@HiltViewModel
class EditHomeworkViewModel @Inject constructor(
    private val updateHomeworkUseCase: UpdateHomeworkUseCase,
    private val fetchHomeworksUseCase: FetchHomeworksUseCase
) : BaseViewModel<EditHomeworkState, EditHomeworkIntent, EditHomeworkEffect>() {
    //    private var oldHomework = ""
//    var homework = ""
//    private var lessonId = 0
//    private var studentId = 0
//
//    private val _homeworksState = MutableUIStateFlow<List<LessonStudentUI>>()
//    val homeworksState = _homeworksState.asStateFlow()
//
//    private val _updateState = MutableUIStateFlow<Unit>()
//    val updateState = _updateState.asStateFlow()
//
//    fun fetchHomeworks() = fetchHomeworksUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_homeworksState) { list ->
//        list.map { it.toUi() }
//    }
//
//    fun updateHomework() = updateHomeworkUseCase(studentId, lessonId, homework).collectNetworkRequest(_updateState)
//
//    fun isUpdateNeeded() = oldHomework != homework
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
//
//    fun getStudentId() : Int
//    {
//        return studentId
//    }
//
//    fun getLessonId() : Int
//    {
//        return lessonId
//    }
//
//    fun setOldHomework(homework: String)
//    {
//        oldHomework = homework
//    }
    override val initialState: EditHomeworkState = EditHomeworkState()

    override suspend fun handleIntent(intent: EditHomeworkIntent) {
        when (intent) {
            else -> {}
        }
    }
}