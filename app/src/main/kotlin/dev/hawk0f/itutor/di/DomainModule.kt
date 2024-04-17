package dev.hawk0f.itutor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.hawk0f.itutor.features.auth.domain.usecases.AuthUserUseCase
import dev.hawk0f.itutor.features.register.domain.usecases.RegisterUserUseCase
import dev.hawk0f.itutor.features.register.domain.repositories.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule
{
    @Provides
    fun provideAuthUserUseCase(repository: dev.hawk0f.itutor.features.auth.domain.repositories.UserRepository) = AuthUserUseCase(repository)

    @Provides
    fun provideRegisterUserUseCase(repository: UserRepository) = RegisterUserUseCase(repository)
}