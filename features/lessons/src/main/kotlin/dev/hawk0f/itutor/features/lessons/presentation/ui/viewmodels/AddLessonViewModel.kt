package dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.extensions.parseToDate
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.parseToTime
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.core.presentation.models.SubjectUI
import dev.hawk0f.itutor.core.presentation.models.toUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.lessons.domain.usecases.AddLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchSubjectsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonStudentsUseCase
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class AddLessonViewModel @Inject constructor(private val addLessonUseCase: AddLessonUseCase, private val fetchSubjectsUseCase: FetchSubjectsUseCase, private val fetchLessonStudentsUseCase: FetchLessonStudentsUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    var date: String = LocalDate.now().parseToFormat("dd.MM.yyyy")
    var startTime: String = LocalTime.now().parseToFormat("HH:mm")
    var endTime: String = LocalTime.now().plusHours(1).parseToFormat("HH:mm")
    var subject = ""
    private var subjectId = 0
    private var studentsIds = ArrayList<Int>()

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    private val _subjectState = MutableUIStateFlow<List<SubjectUI>>()
    val subjectState = _subjectState.asStateFlow()

    private val _lessonStudentsState = MutableUIStateFlow<List<LessonStudentUI>>()
    val lessonStudentsState = _lessonStudentsState.asStateFlow()

    private val _errorState = MutableLiveData<String?>(null)
    val errorState: LiveData<String?> = _errorState

    fun fetchSubjects() = fetchSubjectsUseCase().collectNetworkRequestWithMapping(_subjectState) { list ->
        list.map { it.toUi() }
    }

    fun fetchLessonStudents()
    {
        fetchLessonStudentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
            list.map { it.toUI(studentsIds.count()) }
        }
    }

    fun onLessonAdd()
    {
        if (studentsIds.isEmpty())
        {
            _errorState.value = "Выберите учеников"
        }
        else if (subjectId == 0)
        {
            _errorState.value = "Выберите предмет"
        }
        else
        {
            addLessonUseCase(LessonDTO(id = 0, date = date.parseToDate("dd.MM.yyyy"), startTime = startTime.parseToTime("HH:mm"), durationInMinutes = ChronoUnit.MINUTES.between(startTime.parseToTime("HH:mm"), endTime.parseToTime("HH:mm")), studentsIds = studentsIds, subjectId = subjectId, userId = currentUser.getUserId())).collectNetworkRequest(_addState)
        }
    }

    fun clearErrorText()
    {
        _errorState.value = null
    }

    fun setStudentsIds(ids: ArrayList<Int>)
    {
        studentsIds.clear()
        studentsIds.addAll(ids)
    }

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

    fun getSubjectId(): Int
    {
        return subjectId
    }

    fun setupFields(studentIds: ArrayList<Int>?, date: String?, startTime: String?, endTime: String?, subjectId: Int, subject: String?)
    {
        studentsIds.clear()
        studentIds?.let {
            studentsIds.addAll(studentIds)
        }

        this.date = date ?: LocalDate.now().parseToFormat("dd.MM.yyyy")
        this.startTime = startTime ?: LocalTime.now().parseToFormat("HH:mm")
        this.endTime = endTime ?: LocalTime.now().plusHours(1).parseToFormat("HH:mm")
        this.subjectId = subjectId
        this.subject = subject ?: ""
    }
}