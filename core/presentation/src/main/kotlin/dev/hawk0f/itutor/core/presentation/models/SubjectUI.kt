package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Subject
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class SubjectUI(
    override val id: Int,
    val subjectName: String
) : IBaseDiffModel<Int>

fun Subject.toUi() = SubjectUI(id, subjectName)