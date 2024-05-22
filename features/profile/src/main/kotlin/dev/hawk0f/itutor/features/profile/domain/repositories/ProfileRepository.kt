package dev.hawk0f.itutor.features.profile.domain.repositories

import dev.hawk0f.itutor.core.data.models.UserDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.User

interface ProfileRepository
{
    fun getUserById(id: Int) : RemoteWrapper<User>
    fun updateUser(userDTO: UserDTO) : RemoteWrapper<Unit>
}