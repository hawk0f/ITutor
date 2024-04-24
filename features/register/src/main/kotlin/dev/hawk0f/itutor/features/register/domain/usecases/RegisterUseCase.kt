package dev.hawk0f.itutor.features.register.domain.usecases

import dev.hawk0f.itutor.core.data.models.UserDTO
import dev.hawk0f.itutor.features.register.domain.repositories.UserRegisterRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRegisterRepository)
{
    operator fun invoke(userDTO: UserDTO) = repository.registerUser(userDTO)
}