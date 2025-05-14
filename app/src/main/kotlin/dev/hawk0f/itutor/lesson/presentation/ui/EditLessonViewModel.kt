package dev.hawk0f.itutor.lesson.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.lesson.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.FetchSubjectsUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.GetLessonByIdUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.UpdateLessonUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class EditLessonState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class EditLessonIntent {
    data class StudentClick(val studentId: Int) : AddLessonIntent()
    data class StudentDeleteClick(val studentId: Int) : AddLessonIntent()
    data object AddStudentClick : AddLessonIntent()
    data object FetchStudents : AddLessonIntent()
}

sealed class EditLessonEffect {
    data class NavigateToEditStudent(val studentId: Int) : AddLessonEffect()
    data object NavigateToAddStudent : AddLessonEffect()
    data class ShowMessage(val message: String) : AddLessonEffect()
}

@HiltViewModel
class EditLessonViewModel @Inject constructor(
    private val fetchSubjectsUseCase: FetchSubjectsUseCase,
    private val fetchLessonStudentsUseCase: FetchLessonStudentsUseCase,
    private val getLessonByIdUseCase: GetLessonByIdUseCase,
    private val updateLessonUseCase: UpdateLessonUseCase,
    val validateIsEmpty: ValidateIsEmpty
) : BaseViewModel<EditLessonState, AddLessonIntent, AddLessonEffect>() {
    //    private var oldParserDate = ""
//    private var oldStartTime = ""
//    private var oldEndTime = ""
//    private var oldStudentsIds = listOf<Int>()
//    private var oldSubjectId = 0
//    private var id = 0
//    var parsedDate: String = LocalDate.now().parseToFormat("dd.MM.yyyy")
//    var date: LocalDate = LocalDate.now()
//    var startTime: String = LocalTime.now().parseToFormat("HH:mm")
//    var endTime: String = LocalTime.now().plusHours(1).parseToFormat("HH:mm")
//    var subject = ""
//    private var subjectId = 0
//    private var studentsIds = mutableListOf<Int>()
//    private var userId = 0
//
//    var allStudents = ArrayList<StudentInLessonUI>()
//
//    private val _lessonState = MutableUIStateFlow<LessonUI>()
//    val lessonState = _lessonState.asStateFlow()
//
//    private val _updateState = MutableUIStateFlow<Unit>()
//    val updateState = _updateState.asStateFlow()
//
//    private val _subjectState = MutableUIStateFlow<List<SubjectUI>>()
//    val subjectState = _subjectState.asStateFlow()
//
//    private val _lessonStudentsState = MutableUIStateFlow<List<StudentInLessonUI>>()
//    val lessonStudentsState = _lessonStudentsState.asStateFlow()
//
//    fun fetchSubjects() = fetchSubjectsUseCase().collectNetworkRequestWithMapping(_subjectState) { list ->
//        list.map { it.toUi() }
//    }
//
//    fun fetchLessonStudents()
//    {
//        fetchLessonStudentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
//            list.map { it.toUI(studentsIds.size) }
//        }
//    }
//
//    fun getLessonById(lessonId: Int) = getLessonByIdUseCase(lessonId).collectNetworkRequestWithMapping(_lessonState) {
//        it.toUi()
//    }
//
//    fun updateLesson() = updateLessonUseCase(LessonDTO(id = id, date = parsedDate.parseToDate("dd.MM.yyyy"), startTime = startTime.parseToTime("HH:mm"), durationInMinutes = MINUTES.between(startTime.parseToTime("HH:mm"), endTime.parseToTime("HH:mm")), studentsIds = studentsIds, subjectId = subjectId, userId = userId)).collectNetworkRequest(_updateState)
//
//    fun getStudentsIds(): List<Int>
//    {
//        return studentsIds
//    }
//
//    fun removeStudentId(id: Int)
//    {
//        studentsIds.remove(id)
//    }
//
//    fun isUpdateNeeded() : Boolean {
//        return oldParserDate != parsedDate || oldStartTime != startTime || oldEndTime != endTime || oldSubjectId != subjectId || oldStudentsIds != studentsIds
//    }
//
//    fun setSubjectId(id: Int)
//    {
//        subjectId = id
//    }
//
//    fun getCurrentLesson(): LessonUI
//    {
//        return LessonUI(id, parsedDate, date, startTime, endTime, studentsIds, "", Subject(subjectId, subject), CurrentUser.getUserId())
//    }
//
//    fun setLesson(lesson: LessonUI)
//    {
//        id = lesson.id
//        date = lesson.date
//        parsedDate = lesson.date.parseToFormat("dd.MM.yyyy")
//        startTime = lesson.startTime
//        endTime = lesson.endTime
//        studentsIds.clear()
//        studentsIds.addAll(lesson.studentsIds)
//        subjectId = lesson.subject.id
//        subject = lesson.subject.subjectName
//        userId = lesson.userId
//        oldParserDate = parsedDate
//        oldStartTime = startTime
//        oldEndTime = endTime
//        oldSubjectId = subjectId
//    }
//
//    fun setOldStudentIds(list: List<Int>)
//    {
//        oldStudentsIds = list
//    }
    override val initialState: EditLessonState = EditLessonState()

    override suspend fun handleIntent(intent: AddLessonIntent) {
        when (intent) {
            else -> {}
        }
    }
}