package dev.hawk0f.itutor.homework.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.auth.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.finance.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.finance.presentation.models.toUi
import dev.hawk0f.itutor.homework.domain.usecases.AddHomeworkUseCase
import dev.hawk0f.itutor.homework.domain.usecases.FetchHomeworksUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddHomeworkViewModel @Inject constructor(private val addHomeworkUseCase: AddHomeworkUseCase, private val fetchHomeworksUseCase: FetchHomeworksUseCase, val validateIsEmpty: ValidateIsEmpty) : BaseViewModel()
{
    private var lessonId = 0
    private var studentId = 0

    private val _homeworksState = MutableUIStateFlow<List<LessonStudentUI>>()
    val homeworksState = _homeworksState.asStateFlow()

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    fun fetchHomeworks() = fetchHomeworksUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_homeworksState) { list ->
        list.map { it.toUi() }
    }

    fun addHomework(homework: String) = addHomeworkUseCase(studentId, lessonId, homework).collectNetworkRequest(_addState)

    fun setLessonId(lessonId: Int)
    {
        this.lessonId = lessonId
    }

    fun setStudentId(studentId: Int)
    {
        this.studentId = studentId
    }
}