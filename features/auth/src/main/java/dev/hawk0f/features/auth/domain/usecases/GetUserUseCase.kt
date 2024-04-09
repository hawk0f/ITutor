package dev.hawk0f.features.auth.domain.usecases

import dev.hawk0f.features.auth.domain.repositories.UserRepository

class GetUserUseCase(private val repository: UserRepository)
{
    operator fun invoke(id: Int) = repository.getUserById(id)
}