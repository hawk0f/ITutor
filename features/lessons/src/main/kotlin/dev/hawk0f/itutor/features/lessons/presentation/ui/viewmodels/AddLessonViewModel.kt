package dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.SubjectUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.lessons.domain.usecases.AddLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchSubjectsUseCase
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

private val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
private val tdf = DateTimeFormatter.ofPattern("HH:mm")

@HiltViewModel
class AddLessonViewModel @Inject constructor(private val addLessonUseCase: AddLessonUseCase, private val fetchSubjectsUseCase: FetchSubjectsUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    var date: String = LocalDate.now().format(dtf)

    var startTime: String = LocalTime.now().format(tdf)
    var endTime: String = LocalTime.now().plusHours(1).format(tdf)
    var subject = ""
    private var subjectId = 0
    private var studentsIds = ArrayList<Int>()

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    private val _subjectState = MutableUIStateFlow<List<SubjectUI>>()
    val subjectState = _subjectState.asStateFlow()

    fun fetchSubjects() = fetchSubjectsUseCase().collectNetworkRequestWithMapping(_subjectState) { list ->
        list.map { it.toUi() }
    }

    fun onLessonAdd() = addLessonUseCase(LessonDTO(date = LocalDate.parse(date), startTime = LocalTime.parse(startTime), durationInMinutes = ChronoUnit.MINUTES.between(LocalTime.parse(endTime), LocalTime.parse(startTime)), studentsIds = studentsIds, subjectId = subjectId, userId = currentUser.getUserId())).collectNetworkRequest(_addState)

    fun setStudentsIds(ids: List<Int>)
    {
        studentsIds.clear()
        studentsIds.addAll(ids)
    }

    fun setSubjectId(id: Int)
    {
        subjectId = id
    }

    fun clearFields()
    {
        date = LocalDate.now().format(dtf)
        startTime = LocalTime.now().format(tdf)
        endTime = LocalTime.now().plusHours(1).format(tdf)
        subject = ""
        subjectId = 0
        studentsIds.clear()
    }
}