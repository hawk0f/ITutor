package dev.hawk0f.itutor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.features.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.features.auth.domain.usecases.AuthUserUseCase
import dev.hawk0f.itutor.features.register.domain.usecases.RegisterUserUseCase
import dev.hawk0f.itutor.features.register.domain.repositories.UserRegisterRepository
import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import dev.hawk0f.itutor.features.students.domain.usecases.AddStudentUseCase
import dev.hawk0f.itutor.features.students.domain.usecases.DeleteStudentUseCase
import dev.hawk0f.itutor.features.students.domain.usecases.FetchStudentsUseCase
import dev.hawk0f.itutor.features.students.domain.usecases.GetStudentByIdUseCase
import dev.hawk0f.itutor.features.students.domain.usecases.UpdateStudentUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule
{
    @Provides
    fun provideCurrentUser() = CurrentUser

    @Provides
    fun provideAuthUserUseCase(repository: UserAuthRepository) = AuthUserUseCase(repository)

    @Provides
    fun provideRegisterUserUseCase(repository: UserRegisterRepository) = RegisterUserUseCase(repository)

    @Provides
    fun provideAddStudentUseCase(repository: StudentsRepository) = AddStudentUseCase(repository)

    @Provides
    fun provideDeleteStudentUseCase(repository: StudentsRepository) = DeleteStudentUseCase(repository)

    @Provides
    fun provideFetchStudentUseCase(repository: StudentsRepository) = FetchStudentsUseCase(repository)

    @Provides
    fun provideGetStudentByIdUseCase(repository: StudentsRepository) = GetStudentByIdUseCase(repository)

    @Provides
    fun provideUpdateStudentUseCase(repository: StudentsRepository) = UpdateStudentUseCase(repository)
}