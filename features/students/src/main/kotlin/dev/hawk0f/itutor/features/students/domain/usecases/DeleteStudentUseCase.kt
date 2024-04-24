package dev.hawk0f.itutor.features.students.domain.usecases

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import javax.inject.Inject

class DeleteStudentUseCase @Inject constructor(private val repository: StudentsRepository)
{
    operator fun invoke(studentId: Int) = repository.deleteStudent(studentId)
}