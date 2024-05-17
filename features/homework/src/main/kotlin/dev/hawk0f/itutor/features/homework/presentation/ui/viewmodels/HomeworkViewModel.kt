package dev.hawk0f.itutor.features.homework.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.core.presentation.models.StudentHomeworksUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.homework.domain.usecases.FetchHomeworksUseCase
import dev.hawk0f.itutor.features.homework.domain.usecases.UpdateHomeworkStatusUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(private val fetchLessonStudentsUseCase: FetchHomeworksUseCase, private val updateHomeworkStatusUseCase: UpdateHomeworkStatusUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _lessonStudentsState = MutableUIStateFlow<List<StudentHomeworksUI>>()
    val lessonStudentsState = _lessonStudentsState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    fun fetchLessonStudents() = fetchLessonStudentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
        val studentHomeworksList = ArrayList<StudentHomeworksUI>()
        list.forEach { lessonStudent ->
            if (lessonStudent.homework.isNotEmpty())
            {
                val lessonStudentUI = lessonStudent.toUi()
                val currentStudentHomeworks = studentHomeworksList.firstOrNull { it.studentName == lessonStudentUI.studentName }
                if (currentStudentHomeworks != null)
                {
                    currentStudentHomeworks.homeworks.add(lessonStudentUI)
                }
                else
                {
                    val newStudentHomeworks = StudentHomeworksUI(lessonStudentUI.parsedDate, homeworks = ArrayList<LessonStudentUI>().apply {
                        add(lessonStudentUI)
                    })
                    studentHomeworksList.add(newStudentHomeworks)
                }
            }
        }
        studentHomeworksList
    }

    fun updateHomeworkStatus(studentId: Int, lessonId: Int, isHomeworkDone: Boolean) = updateHomeworkStatusUseCase(studentId, lessonId, isHomeworkDone).collectNetworkRequest(_updateState)
}