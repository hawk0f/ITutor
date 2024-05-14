package dev.hawk0f.itutor.core.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.domain.models.Note
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NoteDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("header")
    val header: String,
    @SerialName("text")
    val text: String,
    @SerialName("userId")
    val userId: Int
) : DataMapper<Note>
{
    override fun toDomain(): Note = Note(id, header, text, userId)
}