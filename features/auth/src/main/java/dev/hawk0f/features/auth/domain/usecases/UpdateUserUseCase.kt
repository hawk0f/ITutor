package dev.hawk0f.features.auth.domain.usecases

import dev.hawk0f.features.auth.data.network.models.UserDTO
import dev.hawk0f.features.auth.domain.repositories.UserRepository

class UpdateUserUseCase(private val repository: UserRepository)
{
    operator fun invoke(userDTO: UserDTO) = repository.updateUser(userDTO)
}