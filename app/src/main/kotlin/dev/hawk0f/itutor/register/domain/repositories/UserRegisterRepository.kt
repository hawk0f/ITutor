package dev.hawk0f.itutor.register.domain.repositories

import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.core.domain.RemoteWrapper

interface UserRegisterRepository
{
    fun registerUser(userDTO: UserDTO): RemoteWrapper<User>
}