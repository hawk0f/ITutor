package dev.hawk0f.itutor.student.data.repositories

import dev.hawk0f.itutor.student.data.api.StudentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.student.domain.repositories.StudentsRepository
import dev.hawk0f.itutor.student.data.models.StudentDTO
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(private val service: StudentService) : BaseRepository(),
                                                                                        StudentsRepository
{
    override fun fetchStudents(userId: Int) = doNetworkRequestForList {
        service.fetchStudents(userId)
    }

    override fun getStudentById(studentId: Int) = doNetworkRequestWithMapping {
        service.getStudentById(studentId)
    }

    override fun deleteStudent(studentId: Int) = doNetworkRequestUnit {
        service.deleteStudent(studentId)
    }

    override fun addStudent(student: StudentDTO) = doNetworkRequestUnit {
        service.addStudent(student)
    }

    override fun updateStudent(student: StudentDTO) = doNetworkRequestUnit {
        service.updateStudent(student)
    }
}