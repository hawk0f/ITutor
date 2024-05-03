package dev.hawk0f.itutor.core.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.domain.models.Lesson
import dev.hawk0f.itutor.core.domain.models.LessonStudent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LessonStudentDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("price")
    val price: Float
) : DataMapper<LessonStudent>
{
    override fun toDomain(): LessonStudent = LessonStudent(id, name, fullName, price)
}
