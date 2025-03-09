package dev.hawk0f.itutor.register.data.repositories

import dev.hawk0f.itutor.auth.data.api.UserService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.register.domain.repositories.UserRegisterRepository
import javax.inject.Inject

class UserRegisterRepositoryImpl @Inject constructor(
    private val service: UserService
) : BaseRepository(), UserRegisterRepository
{
    override fun registerUser(userDTO: UserDTO): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.registerUser(userDTO)
    }
}