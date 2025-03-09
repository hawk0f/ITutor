package dev.hawk0f.itutor.homework.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.finance.presentation.models.LessonStudentUI

data class StudentHomeworksUI(
    val studentName: String,
    override val id: String = studentName,
    val homeworks: MutableList<LessonStudentUI>
) : IBaseDiffModel<String>