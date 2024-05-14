package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int

public class NoteFragmentDirections private constructor() {
  private data class ActionNoteFragmentToEditNoteFragment(
    public val noteId: Int,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_noteFragment_to_editNoteFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("noteId", this.noteId)
        return result
      }
  }

  public companion object {
    public fun actionNoteFragmentToAddNoteFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_noteFragment_to_addNoteFragment)

    public fun actionNoteFragmentToEditNoteFragment(noteId: Int): NavDirections =
        ActionNoteFragmentToEditNoteFragment(noteId)
  }
}