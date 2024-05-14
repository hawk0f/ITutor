package dev.hawk0f.itutor.core.data.apiservices

import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.data.models.LessonStudentDTO
import dev.hawk0f.itutor.core.data.models.NoteDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface NoteService
{
    @GET("Notes/getAllNotes")
    suspend fun fetchNotes(@Query("userId") userId: Int): Response<List<NoteDTO>>

    @GET("Notes/getNoteById")
    suspend fun getNoteById(@Query("noteId") noteId: Int): Response<NoteDTO>

    @POST("Notes/addNote")
    suspend fun addNote(@Body noteDTO: NoteDTO): Response<Unit>

    @PUT("Notes/updateNote")
    suspend fun updateNote(@Body noteDTO: NoteDTO): Response<Unit>

    @DELETE("Notes/deleteNote")
    suspend fun deleteNote(@Query("noteId") noteId: Int): Response<Unit>
}