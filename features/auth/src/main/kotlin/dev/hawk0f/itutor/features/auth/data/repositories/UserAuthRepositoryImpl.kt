package dev.hawk0f.itutor.features.auth.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.UserService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.User
import dev.hawk0f.itutor.features.auth.domain.repositories.UserAuthRepository
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(private val service: UserService) : BaseRepository(), UserAuthRepository
{
    override fun authUser(email: String, password: String): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.authUser(email, password)
    }
}