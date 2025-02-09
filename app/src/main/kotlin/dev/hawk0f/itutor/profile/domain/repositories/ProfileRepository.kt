package dev.hawk0f.itutor.profile.domain.repositories

import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.core.domain.RemoteWrapper

interface ProfileRepository
{
    fun getUserById(id: Int) : RemoteWrapper<User>
    fun updateUser(userDTO: UserDTO) : RemoteWrapper<Unit>
}