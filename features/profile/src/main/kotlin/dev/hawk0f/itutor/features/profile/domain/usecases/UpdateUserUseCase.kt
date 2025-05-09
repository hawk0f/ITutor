package dev.hawk0f.itutor.features.profile.domain.usecases

import dev.hawk0f.itutor.core.data.models.UserDTO
import dev.hawk0f.itutor.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val profileRepository: ProfileRepository)
{
    operator fun invoke(userDTO: UserDTO) = profileRepository.updateUser(userDTO)
}