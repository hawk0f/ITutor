package dev.hawk0f.itutor.lesson.presentation.models

import dev.hawk0f.itutor.lesson.domain.models.Subject

data class SubjectUI(
    val id: Int,
    val subjectName: String
)

fun Subject.toUi() = SubjectUI(id, subjectName)