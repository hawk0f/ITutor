package dev.hawk0f.features.auth.data.network.models

import dev.hawk0f.core.data.utils.DataMapper
import dev.hawk0f.features.auth.domain.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("email")
    val email: String,
    @SerialName("phoneNumber")
    val phoneNumber: String?,
    @SerialName("password")
    val password: String
) : DataMapper<User>
{
    override fun toDomain() = User(id, name, surname, email, phoneNumber, password)
}