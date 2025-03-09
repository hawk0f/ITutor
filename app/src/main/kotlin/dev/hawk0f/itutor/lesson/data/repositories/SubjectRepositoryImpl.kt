package dev.hawk0f.itutor.lesson.data.repositories

import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.lesson.domain.repositories.SubjectRepository
import dev.hawk0f.itutor.lesson.data.api.SubjectService
import dev.hawk0f.itutor.lesson.domain.models.Subject
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(private val service: SubjectService) : BaseRepository(),
                                                                                       SubjectRepository
{
    override fun fetchSubjects(): RemoteWrapper<List<Subject>> = doNetworkRequestForList {
        service.fetchSubjects()
    }
}