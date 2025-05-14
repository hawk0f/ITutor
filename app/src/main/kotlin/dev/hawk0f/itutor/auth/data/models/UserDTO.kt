package dev.hawk0f.itutor.auth.data.models

import dev.hawk0f.itutor.auth.domain.models.User
import dev.hawk0f.itutor.core.data.utils.DataMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("email")
    val email: String,
    @SerialName("phoneNumber")
    val phoneNumber: String? = null,
    @SerialName("password")
    val password: String
) : DataMapper<User>
{
    override fun toDomain() = User(
            id,
            name,
            surname,
            email,
            phoneNumber,
            password
    )
}