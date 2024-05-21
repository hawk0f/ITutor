package dev.hawk0f.itutor.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hawk0f.itutor.core.data.apiservices.LessonService
import dev.hawk0f.itutor.core.data.apiservices.NoteService
import dev.hawk0f.itutor.core.data.apiservices.LessonStudentService
import dev.hawk0f.itutor.core.data.apiservices.StudentService
import dev.hawk0f.itutor.core.data.apiservices.SubjectService
import dev.hawk0f.itutor.core.data.apiservices.UserService
import dev.hawk0f.itutor.core.data.local.PreferencesClient
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.data.remote.NetworkClient
import dev.hawk0f.itutor.features.auth.data.repositories.UserAuthRepositoryImpl
import dev.hawk0f.itutor.features.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.features.finance.data.repositories.PaymentRepositoryImpl
import dev.hawk0f.itutor.features.finance.domain.repositories.PaymentRepository
import dev.hawk0f.itutor.features.homework.data.repositories.HomeworkRepositoryImpl
import dev.hawk0f.itutor.features.homework.domain.repositories.HomeworkRepository
import dev.hawk0f.itutor.features.lessons.data.repositories.LessonRepositoryImpl
import dev.hawk0f.itutor.features.lessons.data.repositories.SubjectRepositoryImpl
import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import dev.hawk0f.itutor.features.lessons.domain.repositories.SubjectRepository
import dev.hawk0f.itutor.features.notes.domain.repositories.NoteRepository
import dev.hawk0f.itutor.features.notes.data.repositories.NoteRepositoryImpl
import dev.hawk0f.itutor.features.register.data.repositories.UserRegisterRepositoryImpl
import dev.hawk0f.itutor.features.register.domain.repositories.UserRegisterRepository
import dev.hawk0f.itutor.features.students.data.repositories.StudentsRepositoryImpl
import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule
{
    //User
    @Singleton
    @Provides
    fun provideUserService(client: NetworkClient) = client.provideApiService<UserService>()

    @Singleton
    @Provides
    fun provideAuthUserRepository(service: UserService): UserAuthRepository = UserAuthRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideRegisterUserRepository(service: UserService): UserRegisterRepository = UserRegisterRepositoryImpl(service)

    //Students
    @Singleton
    @Provides
    fun provideStudentRepository(service: StudentService): StudentsRepository = StudentsRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideStudentService(client: NetworkClient) = client.provideApiService<StudentService>()

    //Lessons
    @Singleton
    @Provides
    fun provideLessonRepository(service: LessonService): LessonRepository = LessonRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideLessonService(client: NetworkClient) = client.provideApiService<LessonService>()

    //Subjects
    @Singleton
    @Provides
    fun provideSubjectRepository(service: SubjectService): SubjectRepository = SubjectRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideSubjectService(client: NetworkClient) = client.provideApiService<SubjectService>()

    //Payments
    @Singleton
    @Provides
    fun providePaymentRepository(service: LessonStudentService): PaymentRepository = PaymentRepositoryImpl(service)

    @Singleton
    @Provides
    fun providePaymentService(client: NetworkClient) = client.provideApiService<LessonStudentService>()

    //Notes
    @Singleton
    @Provides
    fun provideNoteService(client: NetworkClient) = client.provideApiService<NoteService>()

    @Singleton
    @Provides
    fun provideNoteRepository(service: NoteService): NoteRepository = NoteRepositoryImpl(service)

    //Homework
    @Singleton
    @Provides
    fun provideHomeworkRepository(service: LessonStudentService): HomeworkRepository = HomeworkRepositoryImpl(service)
    
    //Network Client
    @Singleton
    @Provides
    fun provideNetworkClient(): NetworkClient = NetworkClient()

    // Shared Preferences
    @Singleton
    @Provides
    fun providePreferencesClient(@ApplicationContext context: Context): PreferencesClient = PreferencesClient(context)

    @Singleton
    @Provides
    fun provideUserDataPreferences(preferencesClient: PreferencesClient): UserDataPreferences = UserDataPreferences(preferencesClient)
}