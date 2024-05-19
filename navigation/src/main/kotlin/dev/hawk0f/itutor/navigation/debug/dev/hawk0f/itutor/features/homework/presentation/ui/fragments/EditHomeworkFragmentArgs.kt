package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class EditHomeworkFragmentArgs(
  public val studentId: Int,
  public val lessonId: Int,
  public val homework: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("studentId", this.studentId)
    result.putInt("lessonId", this.lessonId)
    result.putString("homework", this.homework)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("studentId", this.studentId)
    result.set("lessonId", this.lessonId)
    result.set("homework", this.homework)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): EditHomeworkFragmentArgs {
      bundle.setClassLoader(EditHomeworkFragmentArgs::class.java.classLoader)
      val __studentId : Int
      if (bundle.containsKey("studentId")) {
        __studentId = bundle.getInt("studentId")
      } else {
        throw IllegalArgumentException("Required argument \"studentId\" is missing and does not have an android:defaultValue")
      }
      val __lessonId : Int
      if (bundle.containsKey("lessonId")) {
        __lessonId = bundle.getInt("lessonId")
      } else {
        throw IllegalArgumentException("Required argument \"lessonId\" is missing and does not have an android:defaultValue")
      }
      val __homework : String?
      if (bundle.containsKey("homework")) {
        __homework = bundle.getString("homework")
        if (__homework == null) {
          throw IllegalArgumentException("Argument \"homework\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"homework\" is missing and does not have an android:defaultValue")
      }
      return EditHomeworkFragmentArgs(__studentId, __lessonId, __homework)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): EditHomeworkFragmentArgs {
      val __studentId : Int?
      if (savedStateHandle.contains("studentId")) {
        __studentId = savedStateHandle["studentId"]
        if (__studentId == null) {
          throw IllegalArgumentException("Argument \"studentId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"studentId\" is missing and does not have an android:defaultValue")
      }
      val __lessonId : Int?
      if (savedStateHandle.contains("lessonId")) {
        __lessonId = savedStateHandle["lessonId"]
        if (__lessonId == null) {
          throw IllegalArgumentException("Argument \"lessonId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"lessonId\" is missing and does not have an android:defaultValue")
      }
      val __homework : String?
      if (savedStateHandle.contains("homework")) {
        __homework = savedStateHandle["homework"]
        if (__homework == null) {
          throw IllegalArgumentException("Argument \"homework\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"homework\" is missing and does not have an android:defaultValue")
      }
      return EditHomeworkFragmentArgs(__studentId, __lessonId, __homework)
    }
  }
}