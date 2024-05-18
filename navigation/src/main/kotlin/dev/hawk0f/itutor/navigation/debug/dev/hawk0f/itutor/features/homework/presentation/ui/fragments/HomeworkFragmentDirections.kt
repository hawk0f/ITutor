package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class HomeworkFragmentDirections private constructor() {
  public companion object {
    public fun actionHomeworkFragmentToAddHomeworkFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeworkFragment_to_addHomeworkFragment)
  }
}