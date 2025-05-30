package dev.hawk0f.itutor.core.data.apiservices

import dev.hawk0f.itutor.core.data.models.SubjectDTO
import retrofit2.Response
import retrofit2.http.GET

interface SubjectService
{
    @GET("Subjects/getAllSubjects")
    suspend fun fetchSubjects(): Response<List<SubjectDTO>>
}