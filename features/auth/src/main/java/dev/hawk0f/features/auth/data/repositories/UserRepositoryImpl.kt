package dev.hawk0f.features.auth.data.repositories

import dev.hawk0f.core.data.base.BaseRepository
import dev.hawk0f.core.domain.RemoteWrapper
import dev.hawk0f.features.auth.data.network.apiservices.UserService
import dev.hawk0f.features.auth.data.network.models.UserDTO
import dev.hawk0f.features.auth.domain.models.User
import dev.hawk0f.features.auth.domain.repositories.UserRepository

class UserRepositoryImpl(private val service: UserService) : BaseRepository(), UserRepository
{
    override fun getUserById(id: Int): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.getUserById(id)
    }

    override fun authUser(email: String, password: String): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.authUser(email, password)
    }

    override fun registerUser(userDTO: UserDTO): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.registerUser(userDTO)
    }

    override fun updateUser(userDTO: UserDTO): RemoteWrapper<Unit> = doNetworkRequestUnit {
        service.updateUser(userDTO)
    }

    override fun deleteUser(id: Int): RemoteWrapper<Unit> = doNetworkRequestUnit {
        service.deleteUser(id)
    }
}