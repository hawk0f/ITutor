package dev.hawk0f.itutor.navigation

import dev.hawk0f.itutor.navigation.R
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterFragmentToMainContentFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_registerFragment_to_mainContentFragment)
  }
}