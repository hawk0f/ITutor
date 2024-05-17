package dev.hawk0f.itutor.core.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.domain.models.StudentInLesson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StudentInLessonDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("singlePrice")
    val singlePrice: Float,
    @SerialName("groupPrice")
    val groupPrice: Float
) : DataMapper<StudentInLesson>
{
    override fun toDomain(): StudentInLesson = StudentInLesson(id, name, fullName, singlePrice, groupPrice)
}
