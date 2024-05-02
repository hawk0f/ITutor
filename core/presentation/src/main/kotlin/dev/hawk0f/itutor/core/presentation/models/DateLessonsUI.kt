package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class DateLessonsUI(
    val date: String,
    override val id: String = date,
    val lessons: MutableList<LessonUI>
) : IBaseDiffModel<String>