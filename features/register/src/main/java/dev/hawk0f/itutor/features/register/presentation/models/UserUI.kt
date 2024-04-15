package dev.hawk0f.itutor.features.register.presentation.models

import dev.hawk0f.itutor.features.register.domain.models.User

data class UserUI(
    val id: Int,
    val email: String,
    val password: String
)

fun User.toUi() = UserUI(id, email, password)