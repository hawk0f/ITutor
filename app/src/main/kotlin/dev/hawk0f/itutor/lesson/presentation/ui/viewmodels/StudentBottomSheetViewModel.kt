package dev.hawk0f.itutor.lesson.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.lesson.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.lesson.presentation.models.StudentInLessonUI
import dev.hawk0f.itutor.lesson.presentation.models.toUI
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StudentBottomSheetViewModel @Inject constructor(private val getLessonStudentsUseCase: FetchLessonStudentsUseCase) : BaseViewModel()
{
    private val _lessonStudentsState = MutableUIStateFlow<List<StudentInLessonUI>>()
    val lessonStudentsState = _lessonStudentsState.asStateFlow()

    fun getLessonStudents(studentsIds: MutableList<Int>) = getLessonStudentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
        list.map { it.toUI(studentsIds.count()) }
    }
}