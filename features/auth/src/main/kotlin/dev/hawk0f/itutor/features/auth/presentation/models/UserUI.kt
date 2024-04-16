package dev.hawk0f.itutor.features.auth.presentation.models

import dev.hawk0f.itutor.features.auth.domain.models.User

data class UserUI(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String?,
    val password: String
)

fun User.toUi() = UserUI(id, name, surname, email, phoneNumber, password)