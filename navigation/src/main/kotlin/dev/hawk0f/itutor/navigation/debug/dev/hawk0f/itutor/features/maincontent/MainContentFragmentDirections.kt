package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class MainContentFragmentDirections private constructor() {
  public companion object {
    public fun actionMainContentFragmentToAuthFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainContentFragment_to_authFragment)
  }
}