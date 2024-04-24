package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Student
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class StudentUI(
    override val id: Int,
    val fullName: String,
    val age: Int,
    val phoneNumber: String,
    val singlePrice: Double,
    val groupPrice: Double,
    val note: String,
    val userId: Int) : IBaseDiffModel<Int>

fun Student.toUi() = StudentUI(id, "$name $surname", age, phoneNumber, singlePrice, groupPrice, note, userId)