package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.StudentInLesson
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

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