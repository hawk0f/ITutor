package dev.hawk0f.itutor.auth.domain.models

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String?,
    val password: String
)