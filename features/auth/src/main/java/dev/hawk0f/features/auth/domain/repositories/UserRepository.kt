package dev.hawk0f.features.auth.domain.repositories

import dev.hawk0f.core.domain.RemoteWrapper
import dev.hawk0f.features.auth.data.network.models.UserDTO
import dev.hawk0f.features.auth.domain.models.User

interface UserRepository
{
    fun getUserById(id: Int): RemoteWrapper<User>

    fun authUser(email: String, password: String): RemoteWrapper<User>

    fun registerUser(userDTO: UserDTO): RemoteWrapper<User>

    fun updateUser(userDTO: UserDTO): RemoteWrapper<Unit>

    fun deleteUser(id: Int): RemoteWrapper<Unit>
}