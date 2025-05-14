package dev.hawk0f.itutor.profile.data.repositories

import dev.hawk0f.itutor.auth.data.api.UserService
import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val userService: UserService) :
    BaseRepository(), ProfileRepository {

    override fun getUserById(id: Int): RemoteWrapper<User> = doNetworkRequestWithMapping {
        userService.getUserById(id)
    }

    override fun updateUser(userDTO: UserDTO): RemoteWrapper<Unit> = doNetworkRequestUnit {
        userService.updateUser(userDTO)
    }
}