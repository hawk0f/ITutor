package dev.hawk0f.itutor.navigation

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String

public class HomeworkFragmentDirections private constructor() {
  private data class ActionHomeworkFragmentToEditHomeworkFragment(
    public val studentId: Int,
    public val lessonId: Int,
    public val homework: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeworkFragment_to_editHomeworkFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("studentId", this.studentId)
        result.putInt("lessonId", this.lessonId)
        result.putString("homework", this.homework)
        return result
      }
  }

  public companion object {
    public fun actionHomeworkFragmentToAddHomeworkFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeworkFragment_to_addHomeworkFragment)

    public fun actionHomeworkFragmentToEditHomeworkFragment(
      studentId: Int,
      lessonId: Int,
      homework: String,
    ): NavDirections = ActionHomeworkFragmentToEditHomeworkFragment(studentId, lessonId, homework)
  }
}