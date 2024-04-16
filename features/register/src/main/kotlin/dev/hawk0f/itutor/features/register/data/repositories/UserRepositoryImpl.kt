package dev.hawk0f.itutor.features.register.data.repositories

import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.features.register.data.network.apiservices.UserService
import dev.hawk0f.itutor.features.register.data.network.models.UserDTO
import dev.hawk0f.itutor.features.register.domain.models.User
import dev.hawk0f.itutor.features.register.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val service: UserService) : BaseRepository(), UserRepository
{
    override fun registerUser(userDTO: UserDTO): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.registerUser(userDTO)
    }
}