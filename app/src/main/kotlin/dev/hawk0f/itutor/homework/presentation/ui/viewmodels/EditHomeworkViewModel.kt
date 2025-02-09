package dev.hawk0f.itutor.homework.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.auth.presentation.models.toUi
import dev.hawk0f.itutor.finance.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.finance.presentation.models.toUi
import dev.hawk0f.itutor.homework.domain.usecases.FetchHomeworksUseCase
import dev.hawk0f.itutor.homework.domain.usecases.UpdateHomeworkUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditHomeworkViewModel @Inject constructor(private val updateHomeworkUseCase: UpdateHomeworkUseCase, private val fetchHomeworksUseCase: FetchHomeworksUseCase) : BaseViewModel()
{
    private var oldHomework = ""
    var homework = ""
    private var lessonId = 0
    private var studentId = 0

    private val _homeworksState = MutableUIStateFlow<List<LessonStudentUI>>()
    val homeworksState = _homeworksState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    fun fetchHomeworks() = fetchHomeworksUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_homeworksState) { list ->
        list.map { it.toUi() }
    }

    fun updateHomework() = updateHomeworkUseCase(studentId, lessonId, homework).collectNetworkRequest(_updateState)

    fun isUpdateNeeded() = oldHomework != homework

    fun setLessonId(lessonId: Int)
    {
        this.lessonId = lessonId
    }

    fun setStudentId(studentId: Int)
    {
        this.studentId = studentId
    }

    fun getStudentId() : Int
    {
        return studentId
    }

    fun getLessonId() : Int
    {
        return lessonId
    }

    fun setOldHomework(homework: String)
    {
        oldHomework = homework
    }
}