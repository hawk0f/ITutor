package dev.hawk0f.itutor.homework.presentation.models

import dev.hawk0f.itutor.finance.presentation.models.LessonStudentUI

data class StudentHomeworksUI(
    val studentName: String,
    val homeworks: MutableList<LessonStudentUI>
)