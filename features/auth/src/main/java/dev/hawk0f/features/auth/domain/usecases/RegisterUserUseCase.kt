package dev.hawk0f.features.auth.domain.usecases

import dev.hawk0f.features.auth.data.network.models.UserDTO
import dev.hawk0f.features.auth.domain.repositories.UserRepository

class RegisterUserUseCase(private val repository: UserRepository)
{
    operator fun invoke(userDTO: UserDTO) = repository.registerUser(userDTO)
}