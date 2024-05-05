package dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels

import android.text.TextUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.core.presentation.models.toUI
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonStudentsUseCase
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class StudentBottomSheetViewModel @Inject constructor(private val getLessonStudentsUseCase: FetchLessonStudentsUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _lessonStudentsState = MutableUIStateFlow<List<LessonStudentUI>>()
    val lessonStudentsState = _lessonStudentsState.asStateFlow()

    fun getLessonStudents(studentsIds: ArrayList<Int>) = getLessonStudentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
        list.map { it.toUI(studentsIds.count()) }
    }
}