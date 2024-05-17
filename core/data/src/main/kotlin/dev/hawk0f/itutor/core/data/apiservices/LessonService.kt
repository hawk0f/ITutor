package dev.hawk0f.itutor.core.data.apiservices

import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.data.models.StudentInLessonDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LessonService
{
    @GET("Lessons/getAllLessons")
    suspend fun fetchLessons(@Query("userId") userId: Int): Response<List<LessonDTO>>

    @GET("Lessons/getStudentsInLessons")
    suspend fun getLessonStudents(@Query("userId") userId: Int): Response<List<StudentInLessonDTO>>

    @GET("Lessons/getLessonById")
    suspend fun getLessonById(@Query("lessonId") lessonId: Int): Response<LessonDTO>

    @POST("Lessons/addLesson")
    suspend fun addLesson(@Body lessonDTO: LessonDTO): Response<Unit>

    @PUT("Lessons/updateLesson")
    suspend fun updateLesson(@Body lessonDTO: LessonDTO): Response<Unit>

    @DELETE("Lessons/deleteLesson")
    suspend fun deleteLesson(@Query("lessonId") lessonId: Int): Response<Unit>
}