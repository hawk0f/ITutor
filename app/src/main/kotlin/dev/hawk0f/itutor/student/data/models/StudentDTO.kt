package dev.hawk0f.itutor.student.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.student.domain.models.Student
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StudentDTO(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("surname")
    val surname: String = "",
    @SerialName("age")
    val age: Int = 0,
    @SerialName("phoneNumber")
    val phoneNumber: String? = null,
    @SerialName("singlePrice")
    val singlePrice: Double = 0.0,
    @SerialName("groupPrice")
    val groupPrice: Double = 0.0,
    @SerialName("note")
    val note: String? = null,
    @SerialName("userId")
    val userId: Int
) : DataMapper<Student> {
    override fun toDomain(): Student =
        Student(id, name, surname, age, phoneNumber, singlePrice, groupPrice, note, userId)
}