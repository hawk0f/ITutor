package dev.hawk0f.itutor.features.lessons.domain.repositories

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Subject

interface SubjectRepository
{
    fun fetchSubjects(): RemoteWrapper<List<Subject>>
}