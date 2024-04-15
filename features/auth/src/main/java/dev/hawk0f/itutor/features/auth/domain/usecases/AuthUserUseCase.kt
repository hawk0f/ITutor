package dev.hawk0f.itutor.features.auth.domain.usecases

import dev.hawk0f.itutor.features.auth.domain.repositories.UserRepository
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(private val repository: UserRepository)
{
    operator fun invoke(email: String, password: String) = repository.authUser(email, password)
}