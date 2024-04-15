package dev.hawk0f.itutor.features.auth.presentation.models

import dev.hawk0f.itutor.features.auth.domain.models.User

data class UserUI(
    val id: Int,
    val email: String,
    val password: String
)

fun User.toUi() = UserUI(id, email, password)