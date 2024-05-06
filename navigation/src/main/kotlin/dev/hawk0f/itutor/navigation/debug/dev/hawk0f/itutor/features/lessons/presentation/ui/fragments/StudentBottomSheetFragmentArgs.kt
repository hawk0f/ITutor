package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.IntArray
import kotlin.String
import kotlin.jvm.JvmStatic

public data class StudentBottomSheetFragmentArgs(
  public val studentIds: IntArray,
  public val date: String,
  public val startTime: String,
  public val endTime: String,
  public val subjectId: Int,
  public val subject: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putIntArray("studentIds", this.studentIds)
    result.putString("date", this.date)
    result.putString("startTime", this.startTime)
    result.putString("endTime", this.endTime)
    result.putInt("subjectId", this.subjectId)
    result.putString("subject", this.subject)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("studentIds", this.studentIds)
    result.set("date", this.date)
    result.set("startTime", this.startTime)
    result.set("endTime", this.endTime)
    result.set("subjectId", this.subjectId)
    result.set("subject", this.subject)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): StudentBottomSheetFragmentArgs {
      bundle.setClassLoader(StudentBottomSheetFragmentArgs::class.java.classLoader)
      val __studentIds : IntArray?
      if (bundle.containsKey("studentIds")) {
        __studentIds = bundle.getIntArray("studentIds")
        if (__studentIds == null) {
          throw IllegalArgumentException("Argument \"studentIds\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"studentIds\" is missing and does not have an android:defaultValue")
      }
      val __date : String?
      if (bundle.containsKey("date")) {
        __date = bundle.getString("date")
        if (__date == null) {
          throw IllegalArgumentException("Argument \"date\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"date\" is missing and does not have an android:defaultValue")
      }
      val __startTime : String?
      if (bundle.containsKey("startTime")) {
        __startTime = bundle.getString("startTime")
        if (__startTime == null) {
          throw IllegalArgumentException("Argument \"startTime\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"startTime\" is missing and does not have an android:defaultValue")
      }
      val __endTime : String?
      if (bundle.containsKey("endTime")) {
        __endTime = bundle.getString("endTime")
        if (__endTime == null) {
          throw IllegalArgumentException("Argument \"endTime\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"endTime\" is missing and does not have an android:defaultValue")
      }
      val __subjectId : Int
      if (bundle.containsKey("subjectId")) {
        __subjectId = bundle.getInt("subjectId")
      } else {
        throw IllegalArgumentException("Required argument \"subjectId\" is missing and does not have an android:defaultValue")
      }
      val __subject : String?
      if (bundle.containsKey("subject")) {
        __subject = bundle.getString("subject")
        if (__subject == null) {
          throw IllegalArgumentException("Argument \"subject\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"subject\" is missing and does not have an android:defaultValue")
      }
      return StudentBottomSheetFragmentArgs(__studentIds, __date, __startTime, __endTime,
          __subjectId, __subject)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        StudentBottomSheetFragmentArgs {
      val __studentIds : IntArray?
      if (savedStateHandle.contains("studentIds")) {
        __studentIds = savedStateHandle["studentIds"]
        if (__studentIds == null) {
          throw IllegalArgumentException("Argument \"studentIds\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"studentIds\" is missing and does not have an android:defaultValue")
      }
      val __date : String?
      if (savedStateHandle.contains("date")) {
        __date = savedStateHandle["date"]
        if (__date == null) {
          throw IllegalArgumentException("Argument \"date\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"date\" is missing and does not have an android:defaultValue")
      }
      val __startTime : String?
      if (savedStateHandle.contains("startTime")) {
        __startTime = savedStateHandle["startTime"]
        if (__startTime == null) {
          throw IllegalArgumentException("Argument \"startTime\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"startTime\" is missing and does not have an android:defaultValue")
      }
      val __endTime : String?
      if (savedStateHandle.contains("endTime")) {
        __endTime = savedStateHandle["endTime"]
        if (__endTime == null) {
          throw IllegalArgumentException("Argument \"endTime\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"endTime\" is missing and does not have an android:defaultValue")
      }
      val __subjectId : Int?
      if (savedStateHandle.contains("subjectId")) {
        __subjectId = savedStateHandle["subjectId"]
        if (__subjectId == null) {
          throw IllegalArgumentException("Argument \"subjectId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"subjectId\" is missing and does not have an android:defaultValue")
      }
      val __subject : String?
      if (savedStateHandle.contains("subject")) {
        __subject = savedStateHandle["subject"]
        if (__subject == null) {
          throw IllegalArgumentException("Argument \"subject\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"subject\" is missing and does not have an android:defaultValue")
      }
      return StudentBottomSheetFragmentArgs(__studentIds, __date, __startTime, __endTime,
          __subjectId, __subject)
    }
  }
}