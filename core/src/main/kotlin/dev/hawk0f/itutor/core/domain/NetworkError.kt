package dev.hawk0f.itutor.core.domain

import android.content.Context
import androidx.annotation.StringRes
import dev.hawk0f.itutor.core.R

/**
 * Wrapper class for network errors
 */
sealed class NetworkError {

    /**
     * State for Timeout exceptions
     */
    data object Timeout : NetworkError()

    /**
     * State for default errors from server side
     */
    class Api(@StringRes val resName: Int) : NetworkError()

    /**
     * State for unexpected exceptions, for example «HTTP code - 500» or exceptions when mapping models
     */
    data object Unexpected : NetworkError()

    fun getErrorMessage(context: Context): String = when (this) {
        is Timeout, Unexpected -> context.getString(R.string.server_connection_error)
        is Api -> context.getString(resName)
    }
}