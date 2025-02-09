package dev.hawk0f.itutor.note.domain.models

data class Note(
    val id: Int,
    val header: String,
    val text: String,
    val userId: Int
)