package dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.domain.models.Subject
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.extensions.parseToDate
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.parseToTime
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import dev.hawk0f.itutor.core.presentation.models.SubjectUI
import dev.hawk0f.itutor.core.presentation.models.toUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.features.lessons.domain.usecases.AddLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchSubjectsUseCase
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class AddLessonViewModel @Inject constructor(private val addLessonUseCase: AddLessonUseCase, private val fetchSubjectsUseCase: FetchSubjectsUseCase, private val fetchLessonStudentsUseCase: FetchLessonStudentsUseCase, private val currentUser: CurrentUser, val validateIsEmpty: ValidateIsEmpty) : BaseViewModel()
{
    var parsedDate: String = LocalDate.now().parseToFormat("dd.MM.yyyy")
    var date: LocalDate = LocalDate.now()
    var startTime: String = LocalTime.now().parseToFormat("HH:mm")
    var endTime: String = LocalTime.now().plusHours(1).parseToFormat("HH:mm")
    var subject = ""
    private var subjectId = 0
    private var studentsIds = ArrayList<Int>()

    var allStudents = ArrayList<LessonStudentUI>()

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    private val _subjectState = MutableUIStateFlow<List<SubjectUI>>()
    val subjectState = _subjectState.asStateFlow()

    private val _lessonStudentsState = MutableUIStateFlow<List<LessonStudentUI>>()
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

    fun addLesson() = addLessonUseCase(LessonDTO(id = 0, date = parsedDate.parseToDate("dd.MM.yyyy"), startTime = startTime.parseToTime("HH:mm"), durationInMinutes = ChronoUnit.MINUTES.between(startTime.parseToTime("HH:mm"), endTime.parseToTime("HH:mm")), studentsIds = studentsIds, subjectId = subjectId, userId = currentUser.getUserId())).collectNetworkRequest(_addState)

    fun getStudentsIds(): MutableList<Int>
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
        return LessonUI(0, parsedDate, date, startTime, endTime, studentsIds, "", Subject(subjectId, subject), currentUser.getUserId())
    }

    fun setupFields(lessonUI: LessonUI?)
    {
        lessonUI?.let {
            studentsIds.clear()
            studentsIds.addAll(lessonUI.studentsIds)
            date = lessonUI.date
            parsedDate = lessonUI.date.parseToFormat("dd.MM.yyyy")
            startTime = lessonUI.startTime
            endTime = lessonUI.endTime
            subjectId = lessonUI.subject.id
            subject = lessonUI.subject.subjectName
        }
    }
}