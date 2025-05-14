package dev.hawk0f.itutor.student.presentation.models

import dev.hawk0f.itutor.student.domain.models.Student

data class StudentUI(
    val id: Int,
    val fullName: String,
    val age: Int,
    val phoneNumber: String,
    val singlePrice: Double,
    val groupPrice: Double,
    val note: String,
    val userId: Int,
)

fun Student.toUi() = StudentUI(
    id,
    "$name $surname",
    age,
    phoneNumber ?: "",
    singlePrice,
    groupPrice,
    note ?: "",
    userId,
)