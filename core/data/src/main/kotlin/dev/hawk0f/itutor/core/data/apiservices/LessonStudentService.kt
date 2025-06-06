package dev.hawk0f.itutor.core.data.apiservices

import dev.hawk0f.itutor.core.data.models.LessonStudentDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface LessonStudentService
{
    @GET("LessonStudent/getAllLessonStudents")
    suspend fun fetchLessonStudents(@Query("userId") userId: Int): Response<List<LessonStudentDTO>>

    @PUT("LessonStudent/updateLessonStudentPaymentStatus")
    suspend fun updatePaymentStatus(@Query("studentId") studentId: Int, @Query("lessonId") lessonId: Int, @Query("hasPaid") hasPaid: Boolean): Response<Unit>

    @PUT("LessonStudent/updateLessonStudentHomeworkStatus")
    suspend fun updateHomeworkStatus(@Query("studentId") studentId: Int, @Query("lessonId") lessonId: Int, @Query("isHomeworkDone") isHomeworkDone: Boolean): Response<Unit>

    @PUT("LessonStudent/addLessonStudentHomework")
    suspend fun addHomework(@Query("studentId") studentId: Int, @Query("lessonId") lessonId: Int, @Query("homework") homework: String): Response<Unit>

    @PUT("LessonStudent/updateLessonStudentHomework")
    suspend fun updateHomework(@Query("studentId") studentId: Int, @Query("lessonId") lessonId: Int, @Query("homework") homework: String): Response<Unit>
}