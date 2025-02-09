package dev.hawk0f.itutor.student.domain.usecases

import dev.hawk0f.itutor.student.domain.repositories.StudentsRepository
import javax.inject.Inject

class GetStudentByIdUseCase @Inject constructor(private val repository: StudentsRepository)
{
    operator fun invoke(studentId: Int) = repository.getStudentById(studentId)
}