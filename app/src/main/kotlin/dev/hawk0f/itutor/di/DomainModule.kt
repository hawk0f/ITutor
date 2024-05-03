package dev.hawk0f.itutor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.features.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.features.auth.domain.usecases.AuthUserUseCase
import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import dev.hawk0f.itutor.features.lessons.domain.repositories.SubjectRepository
import dev.hawk0f.itutor.features.lessons.domain.usecases.AddLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.DeleteLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchSubjectsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.GetLessonByIdUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.UpdateLessonUseCase
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
    fun provideFetchStudentsUseCase(repository: StudentsRepository) = FetchStudentsUseCase(repository)

    @Provides
    fun provideGetStudentByIdUseCase(repository: StudentsRepository) = GetStudentByIdUseCase(repository)

    @Provides
    fun provideUpdateStudentUseCase(repository: StudentsRepository) = UpdateStudentUseCase(repository)

    @Provides
    fun provideAddLessonUseCase(repository: LessonRepository) = AddLessonUseCase(repository)

    @Provides
    fun provideDeleteLessonUseCase(repository: LessonRepository) = DeleteLessonUseCase(repository)

    @Provides
    fun provideFetchLessonsUseCase(repository: LessonRepository) = FetchLessonsUseCase(repository)

    @Provides
    fun provideGetLessonByIdUseCase(repository: LessonRepository) = GetLessonByIdUseCase(repository)

    @Provides
    fun provideUpdateLessonUseCase(repository: LessonRepository) = UpdateLessonUseCase(repository)

    @Provides
    fun provideFetchSubjectsUseCase(repository: SubjectRepository) = FetchSubjectsUseCase(repository)

    @Provides
    fun provideFetchLessonStudentsUseCase(repository: LessonRepository) = FetchLessonStudentsUseCase(repository)
}