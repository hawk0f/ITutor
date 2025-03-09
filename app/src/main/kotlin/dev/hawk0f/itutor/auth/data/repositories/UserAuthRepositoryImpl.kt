package dev.hawk0f.itutor.auth.data.repositories

import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.auth.data.api.UserService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val service: UserService
) : BaseRepository(), UserAuthRepository
{
    override fun authUser(email: String, password: String): RemoteWrapper<User> = doNetworkRequestWithMapping {
        service.authUser(email, password)
    }
}