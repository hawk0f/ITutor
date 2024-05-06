package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class EditStudentFragmentArgs(
  public val studentId: Int,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("studentId", this.studentId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("studentId", this.studentId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): EditStudentFragmentArgs {
      bundle.setClassLoader(EditStudentFragmentArgs::class.java.classLoader)
      val __studentId : Int
      if (bundle.containsKey("studentId")) {
        __studentId = bundle.getInt("studentId")
      } else {
        throw IllegalArgumentException("Required argument \"studentId\" is missing and does not have an android:defaultValue")
      }
      return EditStudentFragmentArgs(__studentId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): EditStudentFragmentArgs {
      val __studentId : Int?
      if (savedStateHandle.contains("studentId")) {
        __studentId = savedStateHandle["studentId"]
        if (__studentId == null) {
          throw IllegalArgumentException("Argument \"studentId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"studentId\" is missing and does not have an android:defaultValue")
      }
      return EditStudentFragmentArgs(__studentId)
    }
  }
}