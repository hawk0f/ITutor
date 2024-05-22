package dev.hawk0f.itutor.features.profile.domain.usecases

import dev.hawk0f.itutor.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val profileRepository: ProfileRepository)
{
    operator fun invoke(id: Int) = profileRepository.getUserById(id)
}