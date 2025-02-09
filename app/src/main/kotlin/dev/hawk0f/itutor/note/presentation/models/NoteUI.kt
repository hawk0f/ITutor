package dev.hawk0f.itutor.note.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.core.presentation.extensions.takeDot
import dev.hawk0f.itutor.note.domain.models.Note

data class NoteUI(
    override val id: Int,
    val fullHeader: String,
    val fullText: String,
    val userId: Int,
    val shortHeader: String = fullHeader.takeDot(30),
    val shortText: String = fullText.takeDot(30)): IBaseDiffModel<Int>

fun Note.toUi() = NoteUI(id, header, text, userId)