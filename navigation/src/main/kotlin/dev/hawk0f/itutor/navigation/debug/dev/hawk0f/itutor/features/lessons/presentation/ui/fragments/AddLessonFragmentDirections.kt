package dev.hawk0f.itutor.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class AddLessonFragmentDirections private constructor() {
  private data class ActionAddLessonFragmentToStudentBottomSheetFragment(
    public val lesson: LessonUI,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_addLessonFragment_to_studentBottomSheetFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
  }

  public companion object {
    public fun actionAddLessonFragmentToStudentBottomSheetFragment(lesson: LessonUI): NavDirections
        = ActionAddLessonFragmentToStudentBottomSheetFragment(lesson)
  }
}