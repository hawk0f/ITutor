package dev.hawk0f.itutor.lesson.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.lesson.domain.usecases.DeleteLessonUseCase
import dev.hawk0f.itutor.lesson.domain.usecases.FetchLessonsUseCase
import dev.hawk0f.itutor.lesson.presentation.models.DateLessonsUI
import dev.hawk0f.itutor.lesson.presentation.models.LessonUI
import dev.hawk0f.itutor.lesson.presentation.models.toUi
import kotlinx.coroutines.flow.asStateFlow
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(private val fetchLessonsUseCase: FetchLessonsUseCase, private val deleteLessonUseCase: DeleteLessonUseCase) : BaseViewModel()
{
    private val _lessonState = MutableUIStateFlow<List<DateLessonsUI>>()
    val lessonState = _lessonState.asStateFlow()

    private val _deleteState = MutableUIStateFlow<Unit>()
    val deleteState = _deleteState.asStateFlow()

    fun fetchLessons() = fetchLessonsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonState) { list ->
        val dateLessonsList = ArrayList<DateLessonsUI>()
        list.forEach { lesson ->
            val lessonUI = lesson.toUi()
            val currentDateLessons = dateLessonsList.firstOrNull { it.date == lessonUI.parsedDate }
            if (currentDateLessons != null)
            {
                currentDateLessons.lessons.add(lessonUI)
            }
            else
            {
                val newDateLessons = DateLessonsUI(lessonUI.parsedDate, lessons = ArrayList<LessonUI>().apply {
                    add(lessonUI)
                })
                dateLessonsList.add(newDateLessons)
            }
        }
        dateLessonsList
    }

    fun deleteLesson(lessonId: Int) = deleteLessonUseCase(lessonId).collectNetworkRequest(_deleteState)
}