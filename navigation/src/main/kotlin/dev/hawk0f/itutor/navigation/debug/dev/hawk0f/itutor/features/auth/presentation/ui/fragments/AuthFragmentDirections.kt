package dev.hawk0f.itutor.navigation

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class AuthFragmentDirections private constructor() {
  public companion object {
    public fun actionAuthFragmentToRegisterFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_authFragment_to_registerFragment)

    public fun actionAuthFragmentToMainContentFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_authFragment_to_mainContentFragment)
  }
}