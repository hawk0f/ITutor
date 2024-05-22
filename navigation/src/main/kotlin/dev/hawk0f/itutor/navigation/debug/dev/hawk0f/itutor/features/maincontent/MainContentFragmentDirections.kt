package dev.hawk0f.itutor.navigation

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class MainContentFragmentDirections private constructor() {
  public companion object {
    public fun actionMainContentFragmentToProfileFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainContentFragment_to_profileFragment)
  }
}