package dev.hawk0f.itutor.core.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.domain.models.Student
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StudentDTO(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("age")
    val age: Int,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("singlePrice")
    val singlePrice: Double,
    @SerialName("groupPrice")
    val groupPrice: Double,
    @SerialName("note")
    val note: String,
    @SerialName("userId")
    val userId: Int
) : DataMapper<Student>
{
    override fun toDomain(): Student = Student(id, name, surname, age, phoneNumber, singlePrice, groupPrice, note, userId)
}