package dev.hawk0f.itutor.activity

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val userDataPreferences: UserDataPreferences, val currentUser: CurrentUser) : BaseViewModel()