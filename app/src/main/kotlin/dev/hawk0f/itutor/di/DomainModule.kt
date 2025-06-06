package dev.hawk0f.itutor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.hawk0f.itutor.features.auth.domain.repositories.UserAuthRepository
import dev.hawk0f.itutor.features.auth.domain.usecases.AuthUserUseCase
import dev.hawk0f.itutor.features.finance.domain.repositories.PaymentRepository
import dev.hawk0f.itutor.features.finance.domain.usecases.FetchPaymentsUseCase
import dev.hawk0f.itutor.features.finance.domain.usecases.UpdatePaymentStatusUseCase
import dev.hawk0f.itutor.features.homework.domain.repositories.HomeworkRepository
import dev.hawk0f.itutor.features.homework.domain.usecases.AddHomeworkUseCase
import dev.hawk0f.itutor.features.homework.domain.usecases.FetchHomeworksUseCase
import dev.hawk0f.itutor.features.homework.domain.usecases.UpdateHomeworkStatusUseCase
import dev.hawk0f.itutor.features.homework.domain.usecases.UpdateHomeworkUseCase
import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import dev.hawk0f.itutor.features.lessons.domain.repositories.SubjectRepository
import dev.hawk0f.itutor.features.lessons.domain.usecases.AddLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.DeleteLessonUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonStudentsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchLessonsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.FetchSubjectsUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.GetLessonByIdUseCase
import dev.hawk0f.itutor.features.lessons.domain.usecases.UpdateLessonUseCase
import dev.hawk0f.itutor.features.notes.domain.repositories.NoteRepository
import dev.hawk0f.itutor.features.notes.domain.usecases.AddNoteUseCase
import dev.hawk0f.itutor.features.notes.domain.usecases.DeleteNoteUseCase
import dev.hawk0f.itutor.features.notes.domain.usecases.FetchNotesUseCase
import dev.hawk0f.itutor.features.notes.domain.usecases.GetNoteByIdUseCase
import dev.hawk0f.itutor.features.notes.domain.usecases.UpdateNoteUseCase
import dev.hawk0f.itutor.features.profile.domain.usecases.GetUserByIdUseCase
import dev.hawk0f.itutor.features.profile.domain.repositories.ProfileRepository
import dev.hawk0f.itutor.features.profile.domain.usecases.UpdateUserUseCase
import dev.hawk0f.itutor.features.register.domain.repositories.UserRegisterRepository
import dev.hawk0f.itutor.features.register.domain.usecases.RegisterUserUseCase
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
    //User
    @Provides
    fun provideAuthUserUseCase(repository: UserAuthRepository) = AuthUserUseCase(repository)

    @Provides
    fun provideRegisterUserUseCase(repository: UserRegisterRepository) = RegisterUserUseCase(repository)

    @Provides
    fun provideUpdateUserUseCase(repository: ProfileRepository) = UpdateUserUseCase(repository)

    @Provides
    fun provideGetUserByIdUseCase(repository: ProfileRepository) = GetUserByIdUseCase(repository)

    //Students
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

    //Lessons
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
    fun provideFetchLessonStudentsUseCase(repository: LessonRepository) = FetchLessonStudentsUseCase(repository)

    //Subjects
    @Provides
    fun provideFetchSubjectsUseCase(repository: SubjectRepository) = FetchSubjectsUseCase(repository)

    //Payments
    @Provides
    fun provideFetchPaymentsUseCase(repository: PaymentRepository) = FetchPaymentsUseCase(repository)

    @Provides
    fun provideUpdatePaymentUseCase(repository: PaymentRepository) = UpdatePaymentStatusUseCase(repository)

    //Notes
    @Provides
    fun provideFetchNotesUseCase(repository: NoteRepository) = FetchNotesUseCase(repository)

    @Provides
    fun provideGetNoteByIdUseCase(repository: NoteRepository) = GetNoteByIdUseCase(repository)

    @Provides
    fun provideAddNoteUseCase(repository: NoteRepository) = AddNoteUseCase(repository)

    @Provides
    fun provideUpdateNoteUseCase(repository: NoteRepository) = UpdateNoteUseCase(repository)

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteRepository) = DeleteNoteUseCase(repository)

    //Homework
    @Provides
    fun provideUpdateHomeworkStatusUseCase(repository: HomeworkRepository) = UpdateHomeworkStatusUseCase(repository)

    @Provides
    fun provideAddHomeworkUseCase(repository: HomeworkRepository) = AddHomeworkUseCase(repository)

    @Provides
    fun provideUpdateHomeworkUseCase(repository: HomeworkRepository) = UpdateHomeworkUseCase(repository)

    @Provides
    fun provideFetchHomeworksUseCase(repository: HomeworkRepository) = FetchHomeworksUseCase(repository)
}