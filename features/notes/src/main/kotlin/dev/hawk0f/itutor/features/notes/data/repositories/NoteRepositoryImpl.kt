package dev.hawk0f.itutor.features.notes.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.NoteService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.data.models.NoteDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Note
import dev.hawk0f.itutor.features.notes.domain.repositories.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val service: NoteService) : BaseRepository(), NoteRepository
{
    override fun fetchNotes(userId: Int): RemoteWrapper<List<Note>> = doNetworkRequestForList {
        service.fetchNotes(userId)
    }

    override fun getNoteById(noteId: Int): RemoteWrapper<Note> = doNetworkRequestWithMapping {
        service.getNoteById(noteId)
    }

    override fun deleteNote(noteId: Int): RemoteWrapper<Unit> = doNetworkRequestUnit {
        service.deleteNote(noteId)
    }

    override fun addNote(note: NoteDTO): RemoteWrapper<Unit> = doNetworkRequestUnit {
        service.addNote(note)
    }

    override fun updateNote(note: NoteDTO): RemoteWrapper<Unit> = doNetworkRequestUnit {
        service.updateNote(note)
    }
}