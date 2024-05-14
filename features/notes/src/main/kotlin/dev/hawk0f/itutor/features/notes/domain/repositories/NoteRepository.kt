package dev.hawk0f.itutor.features.notes.domain.repositories

import dev.hawk0f.itutor.core.data.models.NoteDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Note

interface NoteRepository
{
    fun fetchNotes(userId: Int) : RemoteWrapper<List<Note>>

    fun getNoteById(noteId: Int) : RemoteWrapper<Note>

    fun deleteNote(noteId: Int) : RemoteWrapper<Unit>

    fun addNote(note: NoteDTO) : RemoteWrapper<Unit>

    fun updateNote(note: NoteDTO) : RemoteWrapper<Unit>
}