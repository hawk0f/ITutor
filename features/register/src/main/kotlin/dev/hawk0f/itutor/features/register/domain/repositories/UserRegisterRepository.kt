package dev.hawk0f.itutor.features.register.domain.repositories

import dev.hawk0f.itutor.core.data.models.UserDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.User

interface UserRegisterRepository
{
    fun registerUser(userDTO: UserDTO): RemoteWrapper<User>
}