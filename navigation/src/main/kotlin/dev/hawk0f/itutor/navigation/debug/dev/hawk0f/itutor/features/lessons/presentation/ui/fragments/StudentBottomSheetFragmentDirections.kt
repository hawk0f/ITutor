package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.IntArray
import kotlin.String

public class StudentBottomSheetFragmentDirections private constructor() {
  private data class ActionStudentBottomSheetFragmentToAddLessonFragment(
    public val studentIds: IntArray? = null,
    public val date: String? = null,
    public val startTime: String? = null,
    public val endTime: String? = null,
    public val subjectId: Int = 0,
    public val subject: String? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_studentBottomSheetFragment_to_addLessonFragment

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
    public fun actionStudentBottomSheetFragmentToAddLessonFragment(
      studentIds: IntArray? = null,
      date: String? = null,
      startTime: String? = null,
      endTime: String? = null,
      subjectId: Int = 0,
      subject: String? = null,
    ): NavDirections = ActionStudentBottomSheetFragmentToAddLessonFragment(studentIds, date,
        startTime, endTime, subjectId, subject)
  }
}