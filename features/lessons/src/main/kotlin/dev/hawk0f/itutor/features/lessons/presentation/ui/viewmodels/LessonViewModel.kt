package dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.DateLessonsUI
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.lessons.domain.usecases.DeleteLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonsUseCase
import kotlinx.coroutines.flow.asStateFlow
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(private val fetchLessonsUseCase: FetchLessonsUseCase, private val deleteLessonUseCase: DeleteLessonUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _lessonState = MutableUIStateFlow<List<DateLessonsUI>>()
    val lessonState = _lessonState.asStateFlow()

    private val _deleteState = MutableUIStateFlow<Unit>()
    val deleteState = _deleteState.asStateFlow()

    fun fetchLessons() = fetchLessonsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonState) { list ->
        val dateLessonsList = ArrayList<DateLessonsUI>()
        list.forEach { lesson ->
            val lessonUI = lesson.toUi()
            val currentDateLessons = dateLessonsList.firstOrNull { it.date == lessonUI.date }
            if (currentDateLessons != null)
            {
                currentDateLessons.lessons.add(lessonUI)
            }
            else
            {
                val newDateLessons = DateLessonsUI(lessonUI.date, lessons = ArrayList<LessonUI>().apply {
                    add(lessonUI)
                })
                dateLessonsList.add(newDateLessons)
            }
        }
        dateLessonsList
    }

    fun deleteLesson(lessonId: Int) = deleteLessonUseCase(lessonId).collectNetworkRequest(_deleteState)
}