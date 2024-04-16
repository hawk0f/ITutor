package dev.hawk0f.itutor.features.register.domain.repositories

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.features.register.data.network.models.UserDTO
import dev.hawk0f.itutor.features.register.domain.models.User

interface UserRepository
{
    fun registerUser(userDTO: UserDTO): RemoteWrapper<User>
}