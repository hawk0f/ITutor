package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Note
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.core.presentation.extensions.takeDot

data class NoteUI(
    override val id: Int,
    val fullHeader: String,
    val fullText: String,
    val userId: Int,
    val shortHeader: String = fullHeader.takeDot(10),
    val shortText: String = fullText.takeDot(10)): IBaseDiffModel<Int>

fun Note.toUi() = NoteUI(id, header, text, userId)