package dev.hawk0f.features.auth.domain.usecases

import dev.hawk0f.features.auth.domain.repositories.UserRepository

class DeleteUserUseCase(private val repository: UserRepository)
{
    operator fun invoke(id: Int) = repository.deleteUser(id)
}