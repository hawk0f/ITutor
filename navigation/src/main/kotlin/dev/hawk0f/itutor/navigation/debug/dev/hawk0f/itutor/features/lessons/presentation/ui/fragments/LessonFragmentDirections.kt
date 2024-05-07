package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import java.io.Serializable
import kotlin.Int
import kotlin.Suppress

public class LessonFragmentDirections private constructor() {
  private data class ActionLessonFragmentToAddLessonFragment(
    public val lesson: LessonUI? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_lessonFragment_to_addLessonFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java)) {
          result.putParcelable("lesson", this.lesson as Parcelable?)
        } else if (Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
          result.putSerializable("lesson", this.lesson as Serializable?)
        }
        return result
      }
  }

  private data class ActionLessonFragmentToEditLessonFragment(
    public val lessonId: Int = 0,
    public val lesson: LessonUI? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_lessonFragment_to_editLessonFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        result.putInt("lessonId", this.lessonId)
        if (Parcelable::class.java.isAssignableFrom(LessonUI::class.java)) {
          result.putParcelable("lesson", this.lesson as Parcelable?)
        } else if (Serializable::class.java.isAssignableFrom(LessonUI::class.java)) {
          result.putSerializable("lesson", this.lesson as Serializable?)
        }
        return result
      }
  }

  public companion object {
    public fun actionLessonFragmentToAddLessonFragment(lesson: LessonUI? = null): NavDirections =
        ActionLessonFragmentToAddLessonFragment(lesson)

    public fun actionLessonFragmentToEditLessonFragment(lessonId: Int = 0, lesson: LessonUI? =
        null): NavDirections = ActionLessonFragmentToEditLessonFragment(lessonId, lesson)
  }
}