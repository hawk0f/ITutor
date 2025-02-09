package dev.hawk0f.itutor.student.data.api

import dev.hawk0f.itutor.student.data.models.StudentDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface StudentService
{
    @GET("Students/getAllStudents")
    suspend fun fetchStudents(@Query("userId") userId: Int): Response<List<StudentDTO>>

    @GET("Students/getStudentById")
    suspend fun getStudentById(@Query("studentId") studentId: Int): Response<StudentDTO>

    @POST("Students/addStudent")
    suspend fun addStudent(@Body studentDTO: StudentDTO): Response<Unit>

    @PUT("Students/updateStudent")
    suspend fun updateStudent(@Body studentDTO: StudentDTO): Response<Unit>

    @DELETE("Students/deleteStudent")
    suspend fun deleteStudent(@Query("studentId") studentId: Int): Response<Unit>
}