package dev.hawk0f.itutor.student.domain.usecases

import dev.hawk0f.itutor.student.domain.repositories.StudentsRepository
import javax.inject.Inject

class FetchStudentsUseCase @Inject constructor(private val repository: StudentsRepository)
{
    operator fun invoke(userId: Int) = repository.fetchStudents(userId)
}
