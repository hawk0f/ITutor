package dev.hawk0f.itutor.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hawk0f.itutor.core.data.remote.NetworkClient
import dev.hawk0f.itutor.features.register.data.network.apiservices.UserService
import dev.hawk0f.itutor.features.register.data.repositories.UserRepositoryImpl
import dev.hawk0f.itutor.features.register.domain.repositories.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule
{
    @Singleton
    @Provides
    fun provideAuthUserRepository(service: dev.hawk0f.itutor.features.auth.data.network.apiservices.UserService): dev.hawk0f.itutor.features.auth.domain.repositories.UserRepository = dev.hawk0f.itutor.features.auth.data.repositories.UserRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideRegisterUserRepository(service: UserService): UserRepository = UserRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideAuthUserService(client: NetworkClient) = client.provideApiService<dev.hawk0f.itutor.features.auth.data.network.apiservices.UserService>()

    @Singleton
    @Provides
    fun provideRegisterUserService(client: NetworkClient) = client.provideApiService<UserService>()

    @Singleton
    @Provides
    fun provideNetworkClient(): NetworkClient = NetworkClient()
}