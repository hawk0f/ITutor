package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class StudentHomeworksUI(
    val studentName: String,
    override val id: String = studentName,
    val homeworks: MutableList<LessonStudentUI>
) : IBaseDiffModel<String>