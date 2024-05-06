package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int

public class StudentFragmentDirections private constructor() {
  private data class ActionStudentsFragmentToEditStudentFragment(
    public val studentId: Int,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_studentsFragment_to_editStudentFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("studentId", this.studentId)
        return result
      }
  }

  public companion object {
    public fun actionStudentsFragmentToEditStudentFragment(studentId: Int): NavDirections =
        ActionStudentsFragmentToEditStudentFragment(studentId)

    public fun actionStudentsFragmentToAddStudentFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_studentsFragment_to_addStudentFragment)
  }
}