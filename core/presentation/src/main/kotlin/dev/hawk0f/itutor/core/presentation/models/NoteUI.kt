package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Note
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class NoteUI(
    override val id: Int,
    val header: String,
    val text: String,
    val userId: Int): IBaseDiffModel<Int>

fun Note.toUi() = NoteUI(id, header, text, userId)