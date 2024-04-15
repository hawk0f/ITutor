package dev.hawk0f.itutor.features.register.domain.usecases

import dev.hawk0f.itutor.features.register.data.network.models.UserDTO
import dev.hawk0f.itutor.features.register.domain.repositories.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRepository)
{
    operator fun invoke(userDTO: UserDTO) = repository.registerUser(userDTO)
}