package dev.hawk0f.itutor.auth.domain.usecases

import dev.hawk0f.itutor.auth.domain.repositories.UserAuthRepository
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(private val repository: UserAuthRepository)
{
    operator fun invoke(email: String, password: String) = repository.authUser(email, password)
}