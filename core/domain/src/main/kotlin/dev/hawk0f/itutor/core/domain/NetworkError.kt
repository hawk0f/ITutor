package dev.hawk0f.itutor.core.domain

/**
 * Wrapper class for network errors
 */
sealed class NetworkError {

    /**
     * State for Timeout exceptions
     */
    data object Timeout : NetworkError()

    /**
     * State for default errors from server size
     */
    class Api(val message: String) : NetworkError()

    /**
     * State for unexpected exceptions, for example «HTTP code - 500» or exceptions when mapping models
     */
    class Unexpected(val message: String) : NetworkError()
}