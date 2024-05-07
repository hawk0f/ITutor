package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class StudentBottomSheetFragmentArgs(
  public val lesson: LessonUI,
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.putParcelable("lesson", this.lesson as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.putSerializable("lesson", this.lesson as Serializable)
    } else {
      throw UnsupportedOperationException(LessonUI::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.set("lesson", this.lesson as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.set("lesson", this.lesson as Serializable)
    } else {
      throw UnsupportedOperationException(LessonUI::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): StudentBottomSheetFragmentArgs {
      bundle.setClassLoader(StudentBottomSheetFragmentArgs::class.java.classLoader)
      val __lesson : LessonUI?
      if (bundle.containsKey("lesson")) {
        if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java) ||
            Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
          __lesson = bundle.get("lesson") as LessonUI?
        } else {
          throw UnsupportedOperationException(LessonUI::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__lesson == null) {
          throw IllegalArgumentException("Argument \"lesson\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"lesson\" is missing and does not have an android:defaultValue")
      }
      return StudentBottomSheetFragmentArgs(__lesson)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        StudentBottomSheetFragmentArgs {
      val __lesson : LessonUI?
      if (savedStateHandle.contains("lesson")) {
        if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java) ||
            Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
          __lesson = savedStateHandle.get<LessonUI?>("lesson")
        } else {
          throw UnsupportedOperationException(LessonUI::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__lesson == null) {
          throw IllegalArgumentException("Argument \"lesson\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"lesson\" is missing and does not have an android:defaultValue")
      }
      return StudentBottomSheetFragmentArgs(__lesson)
    }
  }
}