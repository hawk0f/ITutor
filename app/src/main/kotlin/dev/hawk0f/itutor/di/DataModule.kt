package dev.hawk0f.itutor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hawk0f.itutor.core.data.apiservices.StudentService
import dev.hawk0f.itutor.core.data.apiservices.UserService
import dev.hawk0f.itutor.core.data.remote.NetworkClient
import dev.hawk0f.itutor.features.auth.data.repositories.UserAuthRepositoryImpl
import dev.hawk0f.itutor.features.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.features.register.data.repositories.UserRegisterRepositoryImpl
import dev.hawk0f.itutor.features.register.domain.repositories.UserRegisterRepository
import dev.hawk0f.itutor.features.students.data.repositories.StudentsRepositoryImpl
import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule
{
    @Singleton
    @Provides
    fun provideAuthUserRepository(service: UserService): UserAuthRepository = UserAuthRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideStudentRepository(service: StudentService): StudentsRepository = StudentsRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideRegisterUserRepository(service: UserService): UserRegisterRepository = UserRegisterRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideStudentService(client: NetworkClient) = client.provideApiService<StudentService>()

    @Singleton
    @Provides
    fun provideUserService(client: NetworkClient) = client.provideApiService<UserService>()

    @Singleton
    @Provides
    fun provideNetworkClient(): NetworkClient = NetworkClient()
}