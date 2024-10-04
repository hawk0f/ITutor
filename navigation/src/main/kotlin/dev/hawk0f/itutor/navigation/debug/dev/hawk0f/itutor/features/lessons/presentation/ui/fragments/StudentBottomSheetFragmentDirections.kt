package dev.hawk0f.itutor.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import java.io.Serializable
import kotlin.Int
import kotlin.IntArray
import kotlin.Suppress

public class StudentBottomSheetFragmentDirections private constructor() {
  private data class ActionStudentBottomSheetFragmentToAddLessonFragment(
    public val lesson: LessonUI? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_studentBottomSheetFragment_to_addLessonFragment

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

  private data class ActionStudentBottomSheetFragmentToEditLessonFragment(
    public val lessonId: Int = 0,
    public val lesson: LessonUI? = null,
    public val studentIds: IntArray? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_studentBottomSheetFragment_to_editLessonFragment

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
        result.putIntArray("studentIds", this.studentIds)
        return result
      }
  }

  public companion object {
    public fun actionStudentBottomSheetFragmentToAddLessonFragment(lesson: LessonUI? = null):
        NavDirections = ActionStudentBottomSheetFragmentToAddLessonFragment(lesson)

    public fun actionStudentBottomSheetFragmentToEditLessonFragment(
      lessonId: Int = 0,
      lesson: LessonUI? = null,
      studentIds: IntArray? = null,
    ): NavDirections = ActionStudentBottomSheetFragmentToEditLessonFragment(lessonId, lesson,
        studentIds)
  }
}