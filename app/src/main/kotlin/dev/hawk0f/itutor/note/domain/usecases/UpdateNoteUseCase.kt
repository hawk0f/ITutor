package dev.hawk0f.itutor.note.domain.usecases

import dev.hawk0f.itutor.note.data.models.NoteDTO
import dev.hawk0f.itutor.note.domain.repositories.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: NoteRepository)
{
    operator fun invoke(note: NoteDTO) = repository.updateNote(note)
}