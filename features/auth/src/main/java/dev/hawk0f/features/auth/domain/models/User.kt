package dev.hawk0f.features.auth.domain.models

class User(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String?,
    val password: String
)