package dev.hawk0f.itutor.auth.domain.repositories

import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.core.domain.RemoteWrapper

interface UserAuthRepository
{
    fun authUser(email: String, password: String): RemoteWrapper<User>
}