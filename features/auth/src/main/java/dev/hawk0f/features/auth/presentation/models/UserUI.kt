package dev.hawk0f.features.auth.presentation.models

import dev.hawk0f.features.auth.domain.models.User

data class UserUI(
    val id: Long,
    val email: String,
    val password: String
)

fun User.toUi() = UserUI(id, email, password)