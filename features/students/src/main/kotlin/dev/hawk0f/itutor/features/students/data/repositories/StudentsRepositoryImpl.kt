package dev.hawk0f.itutor.features.students.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.StudentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.data.models.StudentDTO
import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(private val service: StudentService) : BaseRepository(), StudentsRepository
{
    override fun fetchStudents(userId: Int) = doNetworkRequestForList {
        service.getAllStudents(userId)
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