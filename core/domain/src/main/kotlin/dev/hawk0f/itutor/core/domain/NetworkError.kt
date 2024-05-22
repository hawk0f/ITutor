package dev.hawk0f.itutor.core.domain

import androidx.annotation.StringRes

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
}