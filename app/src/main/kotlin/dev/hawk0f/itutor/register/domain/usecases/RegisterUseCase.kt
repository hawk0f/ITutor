package dev.hawk0f.itutor.register.domain.usecases

import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.register.domain.repositories.UserRegisterRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRegisterRepository)
{
    operator fun invoke(userDTO: UserDTO) = repository.registerUser(userDTO)
}