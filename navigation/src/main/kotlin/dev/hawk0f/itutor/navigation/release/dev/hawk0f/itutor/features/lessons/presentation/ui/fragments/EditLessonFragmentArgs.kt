package dev.hawk0f.itutor.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.IntArray
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class EditLessonFragmentArgs(
  public val lessonId: Int = 0,
  public val lesson: LessonUI? = null,
  public val studentIds: IntArray? = null,
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("lessonId", this.lessonId)
    if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.putParcelable("lesson", this.lesson as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.putSerializable("lesson", this.lesson as Serializable?)
    }
    result.putIntArray("studentIds", this.studentIds)
    return result
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("lessonId", this.lessonId)
    if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.set("lesson", this.lesson as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
      result.set("lesson", this.lesson as Serializable?)
    }
    result.set("studentIds", this.studentIds)
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): EditLessonFragmentArgs {
      bundle.setClassLoader(EditLessonFragmentArgs::class.java.classLoader)
      val __lessonId : Int
      if (bundle.containsKey("lessonId")) {
        __lessonId = bundle.getInt("lessonId")
      } else {
        __lessonId = 0
      }
      val __lesson : LessonUI?
      if (bundle.containsKey("lesson")) {
        if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java) ||
            Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
          __lesson = bundle.get("lesson") as LessonUI?
        } else {
          throw UnsupportedOperationException(LessonUI::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __lesson = null
      }
      val __studentIds : IntArray?
      if (bundle.containsKey("studentIds")) {
        __studentIds = bundle.getIntArray("studentIds")
      } else {
        __studentIds = null
      }
      return EditLessonFragmentArgs(__lessonId, __lesson, __studentIds)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): EditLessonFragmentArgs {
      val __lessonId : Int?
      if (savedStateHandle.contains("lessonId")) {
        __lessonId = savedStateHandle["lessonId"]
        if (__lessonId == null) {
          throw IllegalArgumentException("Argument \"lessonId\" of type integer does not support null values")
        }
      } else {
        __lessonId = 0
      }
      val __lesson : LessonUI?
      if (savedStateHandle.contains("lesson")) {
        if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java) ||
            Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
          __lesson = savedStateHandle.get<LessonUI?>("lesson")
        } else {
          throw UnsupportedOperationException(LessonUI::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __lesson = null
      }
      val __studentIds : IntArray?
      if (savedStateHandle.contains("studentIds")) {
        __studentIds = savedStateHandle["studentIds"]
      } else {
        __studentIds = null
      }
      return EditLessonFragmentArgs(__lessonId, __lesson, __studentIds)
    }
  }
}