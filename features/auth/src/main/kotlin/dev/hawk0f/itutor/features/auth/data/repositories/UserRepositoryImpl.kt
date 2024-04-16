package dev.hawk0f.itutor.features.auth.data.repositories

import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.features.auth.data.network.apiservices.UserService
import dev.hawk0f.itutor.features.auth.domain.models.User
import dev.hawk0f.itutor.features.auth.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val service: UserService) : BaseRepository(), UserRepository
{
    override fun authUser(email: String, password: String): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.authUser(email, password)
    }
}