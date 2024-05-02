package dev.hawk0f.itutor.features.lessons.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.SubjectService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Subject
import dev.hawk0f.itutor.features.lessons.domain.repositories.SubjectRepository
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(private val service: SubjectService) : BaseRepository(), SubjectRepository
{
    override fun fetchSubjects(): RemoteWrapper<List<Subject>> = doNetworkRequestForList {
        service.fetchSubjects()
    }
}