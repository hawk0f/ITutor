package dev.hawk0f.itutor.core.data.local

import javax.inject.Inject

class UserDataPreferences @Inject constructor(
    preferencesClient: PreferencesClient,
) {

    private val preferences = preferencesClient.preferences
    private val editor = preferencesClient.editor

    var userId: Int
        get() = preferences.getInt(PreferencesConstants.PREF_USER_ID, 0)
        set(value) = editor.putInt(PreferencesConstants.PREF_USER_ID, value).apply()

    var accessToken: String
        get() = preferences.getString(PreferencesConstants.PREF_ACCESS_TOKEN, "")!!
        set(value) = editor.putString(PreferencesConstants.PREF_ACCESS_TOKEN, value).apply()

    var refreshToken: String
        get() = preferences.getString(PreferencesConstants.PREF_REFRESH_TOKEN, "")!!
        set(value) = editor.putString(PreferencesConstants.PREF_REFRESH_TOKEN, value).apply()
}