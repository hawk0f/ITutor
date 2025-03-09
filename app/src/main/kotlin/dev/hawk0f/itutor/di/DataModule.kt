package dev.hawk0f.itutor.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hawk0f.itutor.auth.data.api.UserService
import dev.hawk0f.itutor.auth.data.repositories.UserAuthRepositoryImpl
import dev.hawk0f.itutor.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.core.data.local.PreferencesClient
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.data.remote.NetworkClient
import dev.hawk0f.itutor.homework.data.repositories.HomeworkRepositoryImpl
import dev.hawk0f.itutor.lesson.data.repositories.LessonRepositoryImpl
import dev.hawk0f.itutor.lesson.data.repositories.SubjectRepositoryImpl
import dev.hawk0f.itutor.lesson.domain.repositories.LessonRepository
import dev.hawk0f.itutor.lesson.domain.repositories.SubjectRepository
import dev.hawk0f.itutor.note.data.repositories.NoteRepositoryImpl
import dev.hawk0f.itutor.note.domain.repositories.NoteRepository
import dev.hawk0f.itutor.profile.data.repositories.ProfileRepositoryImpl
import dev.hawk0f.itutor.profile.domain.repositories.ProfileRepository
import dev.hawk0f.itutor.student.data.repositories.StudentsRepositoryImpl
import dev.hawk0f.itutor.student.domain.repositories.StudentsRepository
import dev.hawk0f.itutor.finance.data.api.LessonStudentService
import dev.hawk0f.itutor.finance.data.repositories.PaymentRepositoryImpl
import dev.hawk0f.itutor.finance.domain.repositories.PaymentRepository
import dev.hawk0f.itutor.homework.domain.repositories.HomeworkRepository
import dev.hawk0f.itutor.lesson.data.api.LessonService
import dev.hawk0f.itutor.lesson.data.api.SubjectService
import dev.hawk0f.itutor.note.data.api.NoteService
import dev.hawk0f.itutor.register.data.repositories.UserRegisterRepositoryImpl
import dev.hawk0f.itutor.register.domain.repositories.UserRegisterRepository
import dev.hawk0f.itutor.student.data.api.StudentService
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

    @Singleton
    @Provides
    fun provideProfileRepository(service: UserService): ProfileRepository = ProfileRepositoryImpl(service)
    
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