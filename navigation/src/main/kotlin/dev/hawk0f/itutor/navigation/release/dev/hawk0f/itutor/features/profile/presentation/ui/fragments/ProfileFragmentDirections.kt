package dev.hawk0f.itutor.navigation

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class ProfileFragmentDirections private constructor() {
  public companion object {
    public fun actionProfileFragmentToAuthFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_profileFragment_to_authFragment)
  }
}