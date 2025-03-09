package dev.hawk0f.itutor.lesson.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.lesson.domain.models.Subject

data class SubjectUI(
    override val id: Int,
    val subjectName: String
) : IBaseDiffModel<Int>

fun Subject.toUi() = SubjectUI(id, subjectName)