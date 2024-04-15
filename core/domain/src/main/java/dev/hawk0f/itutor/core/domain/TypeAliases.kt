package dev.hawk0f.itutor.core.domain

import kotlinx.coroutines.flow.Flow

/**
 * Simple wrapper for convenience of network requests in repositories
 *
 * @see Flow
 * @see Either
 * @see NetworkError
 */
typealias RemoteWrapper<T> = Flow<Either<NetworkError, T>>