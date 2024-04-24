package dev.hawk0f.itutor.features.students.domain.repositories

import dev.hawk0f.itutor.core.data.models.StudentDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Student

interface StudentsRepository
{
    fun fetchStudents(userId: Int): RemoteWrapper<List<Student>>

    fun getStudentById(studentId: Int): RemoteWrapper<Student>

    fun deleteStudent(studentId: Int): RemoteWrapper<Unit>

    fun addStudent(student: StudentDTO): RemoteWrapper<Unit>

    fun updateStudent(student: StudentDTO): RemoteWrapper<Unit>
}