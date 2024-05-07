package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class EditLessonFragmentDirections private constructor() {
  private data class ActionEditLessonFragmentToStudentBottomSheetFragment(
    public val lesson: LessonUI,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_editLessonFragment_to_studentBottomSheetFragment

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
    public fun actionEditLessonFragmentToStudentBottomSheetFragment(lesson: LessonUI): NavDirections
        = ActionEditLessonFragmentToStudentBottomSheetFragment(lesson)
  }
}