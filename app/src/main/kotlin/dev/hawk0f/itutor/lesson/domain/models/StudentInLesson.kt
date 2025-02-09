package dev.hawk0f.itutor.lesson.domain.models

data class StudentInLesson(
    val id: Int,
    val name: String,
    val fullName: String,
    val singlePrice: Float,
    val groupPrice: Float
)