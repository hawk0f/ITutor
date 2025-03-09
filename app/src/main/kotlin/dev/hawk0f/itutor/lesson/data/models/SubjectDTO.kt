package dev.hawk0f.itutor.lesson.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.lesson.domain.models.Subject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("subjectName")
    val subjectName: String
) : DataMapper<Subject>
{
    override fun toDomain(): Subject = Subject(id, subjectName)
}