package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.User
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class UserUI(
    override val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String?,
    val password: String
) : IBaseDiffModel<Int>

fun User.toUi() = UserUI(id, name, surname, email, phoneNumber, password)