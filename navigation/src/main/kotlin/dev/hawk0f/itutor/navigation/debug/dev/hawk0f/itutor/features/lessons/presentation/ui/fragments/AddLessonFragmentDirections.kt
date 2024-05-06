package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.IntArray
import kotlin.String

public class AddLessonFragmentDirections private constructor() {
  private data class ActionAddLessonFragmentToStudentBottomSheetFragment(
    public val studentIds: IntArray,
    public val date: String,
    public val startTime: String,
    public val endTime: String,
    public val subjectId: Int,
    public val subject: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_addLessonFragment_to_studentBottomSheetFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putIntArray("studentIds", this.studentIds)
        result.putString("date", this.date)
        result.putString("startTime", this.startTime)
        result.putString("endTime", this.endTime)
        result.putInt("subjectId", this.subjectId)
        result.putString("subject", this.subject)
        return result
      }
  }

  public companion object {
    public fun actionAddLessonFragmentToStudentBottomSheetFragment(
      studentIds: IntArray,
      date: String,
      startTime: String,
      endTime: String,
      subjectId: Int,
      subject: String,
    ): NavDirections = ActionAddLessonFragmentToStudentBottomSheetFragment(studentIds, date,
        startTime, endTime, subjectId, subject)
  }
}