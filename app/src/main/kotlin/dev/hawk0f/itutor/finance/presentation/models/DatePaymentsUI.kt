package dev.hawk0f.itutor.finance.presentation.models

data class DatePaymentsUI(
    val date: String,
    val payments: MutableList<LessonStudentUI>
)