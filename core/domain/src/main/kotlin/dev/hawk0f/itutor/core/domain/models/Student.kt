package dev.hawk0f.itutor.core.domain.models

class Student(
    val id: Int,
    val name: String,
    val surname: String,
    val age: Int,
    val phoneNumber: String,
    val singlePrice: Double,
    val groupPrice: Double,
    val note: String,
    val userId: Int
)