package dev.hawk0f.itutor.features.students.domain.usecases

import dev.hawk0f.itutor.core.data.models.StudentDTO
import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import javax.inject.Inject

class UpdateStudentUseCase @Inject constructor(private val repository: StudentsRepository)
{
    operator fun invoke(student: StudentDTO) = repository.updateStudent(student)
}