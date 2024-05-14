package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class EditNoteFragmentArgs(
  public val noteId: Int,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("noteId", this.noteId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("noteId", this.noteId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): EditNoteFragmentArgs {
      bundle.setClassLoader(EditNoteFragmentArgs::class.java.classLoader)
      val __noteId : Int
      if (bundle.containsKey("noteId")) {
        __noteId = bundle.getInt("noteId")
      } else {
        throw IllegalArgumentException("Required argument \"noteId\" is missing and does not have an android:defaultValue")
      }
      return EditNoteFragmentArgs(__noteId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): EditNoteFragmentArgs {
      val __noteId : Int?
      if (savedStateHandle.contains("noteId")) {
        __noteId = savedStateHandle["noteId"]
        if (__noteId == null) {
          throw IllegalArgumentException("Argument \"noteId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"noteId\" is missing and does not have an android:defaultValue")
      }
      return EditNoteFragmentArgs(__noteId)
    }
  }
}