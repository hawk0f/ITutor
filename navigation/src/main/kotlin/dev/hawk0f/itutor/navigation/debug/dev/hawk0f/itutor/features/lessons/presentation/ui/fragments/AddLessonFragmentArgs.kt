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

public data class AddLessonFragmentArgs(
  public val studentIds: IntArray? = null,
  public val date: String? = null,
  public val startTime: String? = null,
  public val endTime: String? = null,
  public val subjectId: Int = 0,
  public val subject: String? = null,
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
    public fun fromBundle(bundle: Bundle): AddLessonFragmentArgs {
      bundle.setClassLoader(AddLessonFragmentArgs::class.java.classLoader)
      val __studentIds : IntArray?
      if (bundle.containsKey("studentIds")) {
        __studentIds = bundle.getIntArray("studentIds")
      } else {
        __studentIds = null
      }
      val __date : String?
      if (bundle.containsKey("date")) {
        __date = bundle.getString("date")
      } else {
        __date = null
      }
      val __startTime : String?
      if (bundle.containsKey("startTime")) {
        __startTime = bundle.getString("startTime")
      } else {
        __startTime = null
      }
      val __endTime : String?
      if (bundle.containsKey("endTime")) {
        __endTime = bundle.getString("endTime")
      } else {
        __endTime = null
      }
      val __subjectId : Int
      if (bundle.containsKey("subjectId")) {
        __subjectId = bundle.getInt("subjectId")
      } else {
        __subjectId = 0
      }
      val __subject : String?
      if (bundle.containsKey("subject")) {
        __subject = bundle.getString("subject")
      } else {
        __subject = null
      }
      return AddLessonFragmentArgs(__studentIds, __date, __startTime, __endTime, __subjectId,
          __subject)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AddLessonFragmentArgs {
      val __studentIds : IntArray?
      if (savedStateHandle.contains("studentIds")) {
        __studentIds = savedStateHandle["studentIds"]
      } else {
        __studentIds = null
      }
      val __date : String?
      if (savedStateHandle.contains("date")) {
        __date = savedStateHandle["date"]
      } else {
        __date = null
      }
      val __startTime : String?
      if (savedStateHandle.contains("startTime")) {
        __startTime = savedStateHandle["startTime"]
      } else {
        __startTime = null
      }
      val __endTime : String?
      if (savedStateHandle.contains("endTime")) {
        __endTime = savedStateHandle["endTime"]
      } else {
        __endTime = null
      }
      val __subjectId : Int?
      if (savedStateHandle.contains("subjectId")) {
        __subjectId = savedStateHandle["subjectId"]
        if (__subjectId == null) {
          throw IllegalArgumentException("Argument \"subjectId\" of type integer does not support null values")
        }
      } else {
        __subjectId = 0
      }
      val __subject : String?
      if (savedStateHandle.contains("subject")) {
        __subject = savedStateHandle["subject"]
      } else {
        __subject = null
      }
      return AddLessonFragmentArgs(__studentIds, __date, __startTime, __endTime, __subjectId,
          __subject)
    }
  }
}