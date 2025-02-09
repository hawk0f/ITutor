package dev.hawk0f.itutor.note.domain.usecases

import dev.hawk0f.itutor.note.domain.repositories.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NoteRepository)
{
    operator fun invoke(noteId: Int) = repository.deleteNote(noteId)
}