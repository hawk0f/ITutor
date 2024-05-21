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

    private val allLessonStudents = ArrayList<LessonStudentUI>()

    fun fetchLessonStudents() = fetchLessonStudentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
        allLessonStudents.clear()
        allLessonStudents.addAll(list.map { it.toUi() })

        val studentHomeworksList = ArrayList<StudentHomeworksUI>()
        allLessonStudents.forEach { lessonStudentUi ->
            if (lessonStudentUi.fullHomework.isNotEmpty())
            {
                val currentStudentHomeworks =
                    studentHomeworksList.firstOrNull { it.studentName == lessonStudentUi.studentName }
                if (currentStudentHomeworks != null)
                {
                    currentStudentHomeworks.homeworks.add(lessonStudentUi)
                }
                else
                {
                    val newStudentHomeworks =
                        StudentHomeworksUI(lessonStudentUi.studentName, homeworks = ArrayList<LessonStudentUI>().apply {
                            add(lessonStudentUi)
                        })
                    studentHomeworksList.add(newStudentHomeworks)
                }
            }
        }
        studentHomeworksList
    }

    fun updateHomeworkStatus(studentId: Int, lessonId: Int, isHomeworkDone: Boolean) = updateHomeworkStatusUseCase(studentId, lessonId, isHomeworkDone).collectNetworkRequest(_updateState)
}