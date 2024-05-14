package dev.hawk0f.itutor.features.notes.domain.usecases

import dev.hawk0f.itutor.core.data.models.NoteDTO
import dev.hawk0f.itutor.features.notes.domain.repositories.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: NoteRepository)
{
    operator fun invoke(note: NoteDTO) = repository.updateNote(note)
}