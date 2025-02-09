package dev.hawk0f.itutor.student.domain.usecases

import dev.hawk0f.itutor.student.data.models.StudentDTO
import dev.hawk0f.itutor.student.domain.repositories.StudentsRepository
import javax.inject.Inject

class UpdateStudentUseCase @Inject constructor(private val repository: StudentsRepository)
{
    operator fun invoke(student: StudentDTO) = repository.updateStudent(student)
}