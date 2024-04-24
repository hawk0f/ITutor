package dev.hawk0f.itutor.features.auth.domain.repositories

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.User

interface UserAuthRepository
{
    fun authUser(email: String, password: String): RemoteWrapper<User>
}