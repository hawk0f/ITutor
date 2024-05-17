package dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.domain.models.Subject
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.extensions.parseToDate
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.parseToTime
import dev.hawk0f.itutor.core.presentation.models.StudentInLessonUI
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import dev.hawk0f.itutor.core.presentation.models.SubjectUI
import dev.hawk0f.itutor.core.presentation.models.toUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchSubjectsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.GetLessonByIdUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.UpdateLessonUseCase
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit.MINUTES
import javax.inject.Inject

@HiltViewModel
class EditLessonViewModel @Inject constructor(private val fetchSubjectsUseCase: FetchSubjectsUseCase, private val fetchLessonStudentsUseCase: FetchLessonStudentsUseCase, private val getLessonByIdUseCase: GetLessonByIdUseCase, private val updateLessonUseCase: UpdateLessonUseCase, private val currentUser: CurrentUser, val validateIsEmpty: ValidateIsEmpty) : BaseViewModel()
{
    private var id = 0
    var parsedDate: String = LocalDate.now().parseToFormat("dd.MM.yyyy")
    var date: LocalDate = LocalDate.now()
    var startTime: String = LocalTime.now().parseToFormat("HH:mm")
    var endTime: String = LocalTime.now().plusHours(1).parseToFormat("HH:mm")
    var subject = ""
    private var subjectId = 0
    private var studentsIds = ArrayList<Int>()
    private var userId = 0

    var allStudents = ArrayList<StudentInLessonUI>()

    private val _lessonState = MutableUIStateFlow<LessonUI>()
    val lessonState = _lessonState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    private val _subjectState = MutableUIStateFlow<List<SubjectUI>>()
    val subjectState = _subjectState.asStateFlow()

    private val _lessonStudentsState = MutableUIStateFlow<List<StudentInLessonUI>>()
    val lessonStudentsState = _lessonStudentsState.asStateFlow()

    fun fetchSubjects() = fetchSubjectsUseCase().collectNetworkRequestWithMapping(_subjectState) { list ->
        list.map { it.toUi() }
    }

    fun fetchLessonStudents()
    {
        fetchLessonStudentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
            list.map { it.toUI(studentsIds.size) }
        }
    }

    fun getLessonById(lessonId: Int) = getLessonByIdUseCase(lessonId).collectNetworkRequestWithMapping(_lessonState) {
        it.toUi()
    }

    fun updateLesson() = updateLessonUseCase(LessonDTO(id = id, date = parsedDate.parseToDate("dd.MM.yyyy"), startTime = startTime.parseToTime("HH:mm"), durationInMinutes = MINUTES.between(startTime.parseToTime("HH:mm"), endTime.parseToTime("HH:mm")), studentsIds = studentsIds, subjectId = subjectId, userId = userId)).collectNetworkRequest(_updateState)

    fun getStudentsIds(): ArrayList<Int>
    {
        return studentsIds
    }

    fun removeStudentId(id: Int)
    {
        studentsIds.remove(id)
    }

    fun setSubjectId(id: Int)
    {
        subjectId = id
    }

    fun getCurrentLesson(): LessonUI
    {
        return LessonUI(id, parsedDate, date, startTime, endTime, studentsIds, "", Subject(subjectId, subject), currentUser.getUserId())
    }

    fun setLesson(lesson: LessonUI)
    {
        id = lesson.id
        date = lesson.date
        parsedDate = lesson.date.parseToFormat("dd.MM.yyyy")
        startTime = lesson.startTime
        endTime = lesson.endTime
        studentsIds.clear()
        studentsIds.addAll(lesson.studentsIds)
        subjectId = lesson.subject.id
        subject = lesson.subject.subjectName
        userId = lesson.userId
    }
}