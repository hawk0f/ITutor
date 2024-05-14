package dev.hawk0f.itutor.features.notes.domain.usecases

import dev.hawk0f.itutor.features.notes.domain.repositories.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NoteRepository)
{
    operator fun invoke(noteId: Int) = repository.deleteNote(noteId)
}