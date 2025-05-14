package dev.hawk0f.itutor.lesson.presentation.models

data class DateLessonsUI(
    val date: String,
    val id: String = date,
    val lessons: MutableList<LessonUI>
)