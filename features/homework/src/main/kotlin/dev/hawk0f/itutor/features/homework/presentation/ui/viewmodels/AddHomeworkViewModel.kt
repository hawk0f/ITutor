package dev.hawk0f.itutor.features.homework.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.features.homework.domain.usecases.AddHomeworkUseCase
import dev.hawk0f.itutor.features.homework.domain.usecases.FetchHomeworksUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddHomeworkViewModel @Inject constructor(private val addHomeworkUseCase: AddHomeworkUseCase, private val fetchHomeworksUseCase: FetchHomeworksUseCase, private val currentUser: CurrentUser, val validateIsEmpty: ValidateIsEmpty) : BaseViewModel()
{
    private var lessonId = 0
    private var studentId = 0

    private val _homeworksState = MutableUIStateFlow<List<LessonStudentUI>>()
    val homeworksState = _homeworksState.asStateFlow()

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    fun fetchHomeworks() = fetchHomeworksUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_homeworksState) { list ->
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