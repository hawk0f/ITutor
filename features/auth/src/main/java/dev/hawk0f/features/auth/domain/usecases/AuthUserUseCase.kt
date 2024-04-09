package dev.hawk0f.features.auth.domain.usecases

import dev.hawk0f.features.auth.domain.repositories.UserRepository

class AuthUserUseCase(private val repository: UserRepository)
{
    operator fun invoke(email: String, password: String) = repository.authUser(email, password)
}