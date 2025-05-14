package dev.hawk0f.itutor.lesson.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.lesson.domain.usecases.AddLessonUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.FetchSubjectsUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class AddLessonState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class AddLessonIntent {
    data class StudentClick(val studentId: Int) : AddLessonIntent()
    data class StudentDeleteClick(val studentId: Int) : AddLessonIntent()
    data object AddStudentClick : AddLessonIntent()
    data object FetchStudents : AddLessonIntent()
}

sealed class AddLessonEffect {
    data class NavigateToEditStudent(val studentId: Int) : AddLessonEffect()
    data object NavigateToAddStudent : AddLessonEffect()
    data class ShowMessage(val message: String) : AddLessonEffect()
}

@HiltViewModel
class AddLessonViewModel @Inject constructor(
    private val addLessonUseCase: AddLessonUseCase,
    private val fetchSubjectsUseCase: FetchSubjectsUseCase,
    private val fetchLessonStudentsUseCase: FetchLessonStudentsUseCase,
    val validateIsEmpty: ValidateIsEmpty
) : BaseViewModel<AddLessonState, AddLessonIntent, AddLessonEffect>() {
    //    var parsedDate: String = LocalDate.now().parseToFormat("dd.MM.yyyy")
//    var date: LocalDate = LocalDate.now()
//    var startTime: String = LocalTime.now().parseToFormat("HH:mm")
//    var endTime: String = LocalTime.now().plusHours(1).parseToFormat("HH:mm")
//    var subject = ""
//    private var subjectId = 0
//    private var studentsIds = ArrayList<Int>()
//
//    var allStudents = ArrayList<StudentInLessonUI>()
//
//    private val _addState = MutableUIStateFlow<Unit>()
//    val addState = _addState.asStateFlow()
//
//    private val _subjectState = MutableUIStateFlow<List<SubjectUI>>()
//    val subjectState = _subjectState.asStateFlow()
//
//    private val _lessonStudentsState = MutableUIStateFlow<List<StudentInLessonUI>>()
//    val lessonStudentsState = _lessonStudentsState.asStateFlow()
//
//    fun fetchSubjects() =
//        fetchSubjectsUseCase().collectNetworkRequestWithMapping(_subjectState) { list ->
//            list.map { it.toUi() }
//        }
//
//    fun fetchLessonStudents() {
//        fetchLessonStudentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(
//            _lessonStudentsState
//        ) { list ->
//            list.map { it.toUI(studentsIds.size) }
//        }
//    }
//
//    fun addLesson() = addLessonUseCase(
//        LessonDTO(
//            id = 0,
//            date = parsedDate.parseToDate("dd.MM.yyyy"),
//            startTime = startTime.parseToTime("HH:mm"),
//            durationInMinutes = ChronoUnit.MINUTES.between(
//                startTime.parseToTime("HH:mm"),
//                endTime.parseToTime("HH:mm")
//            ),
//            studentsIds = studentsIds,
//            subjectId = subjectId,
//            userId = CurrentUser.getUserId()
//        )
//    ).collectNetworkRequest(_addState)
//
//    fun getStudentsIds(): MutableList<Int> {
//        return studentsIds
//    }
//
//    fun removeStudentId(id: Int) {
//        studentsIds.remove(id)
//    }
//
//    fun setSubjectId(id: Int) {
//        subjectId = id
//    }
//
//    fun getCurrentLesson(): LessonUI {
//        return LessonUI(
//            0,
//            parsedDate,
//            date,
//            startTime,
//            endTime,
//            studentsIds,
//            "",
//            Subject(subjectId, subject),
//            CurrentUser.getUserId()
//        )
//    }
//
//    fun setupFields(lessonUI: LessonUI?) {
//        lessonUI?.let {
//            studentsIds.clear()
//            studentsIds.addAll(lessonUI.studentsIds)
//            date = lessonUI.date
//            parsedDate = lessonUI.date.parseToFormat("dd.MM.yyyy")
//            startTime = lessonUI.startTime
//            endTime = lessonUI.endTime
//            subjectId = lessonUI.subject.id
//            subject = lessonUI.subject.subjectName
//        }
//    }
    override val initialState: AddLessonState = AddLessonState()

    override suspend fun handleIntent(intent: AddLessonIntent) {
        when (intent) {
            else -> {}
        }
    }
}