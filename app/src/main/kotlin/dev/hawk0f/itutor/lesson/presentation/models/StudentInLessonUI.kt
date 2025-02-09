package dev.hawk0f.itutor.lesson.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.lesson.domain.models.StudentInLesson

data class StudentInLessonUI(
    override val id: Int,
    val name: String,
    val fullName: String,
    val price: Float,
    var isSelected: Boolean
) : IBaseDiffModel<Int>

fun StudentInLesson.toUI(studentsCount: Int): StudentInLessonUI
{
    val price = if (studentsCount > 1) groupPrice else singlePrice
    return StudentInLessonUI(id, name, fullName, price, false)
}