package dev.hawk0f.itutor.core.data.base

import android.util.Log
import dev.hawk0f.itutor.core.data.BuildConfig
import dev.hawk0f.itutor.core.data.R
import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.domain.Either
import dev.hawk0f.itutor.core.domain.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.InterruptedIOException

/**
 * Base class for all repository implements with helper data layer functions
 */
abstract class BaseRepository
{

    /**
     * Do network request with [DataMapper.toDomain]
     *
     * @receiver [doNetworkRequest]
     */
    protected fun <T : DataMapper<S>, S> doNetworkRequestWithMapping(request: suspend () -> Response<T>): Flow<Either<NetworkError, S>> = doNetworkRequest(request) { body ->
        Either.Right(body.toDomain())
    }

    /**
     * Do network request without mapping for primitive types
     *
     * @receiver [doNetworkRequest]
     */
    protected fun <T> doNetworkRequestWithoutMapping(request: suspend () -> Response<T>): Flow<Either<NetworkError, T>> = doNetworkRequest(request) { body ->
        Either.Right(body)
    }

    /**
     * Do network request for [Response] with [List]
     *
     * @receiver [doNetworkRequest]
     */
    protected fun <T : DataMapper<S>, S> doNetworkRequestForList(request: suspend () -> Response<List<T>>): Flow<Either<NetworkError, List<S>>> = doNetworkRequest(request) { body ->
        Either.Right(body.map { it.toDomain() })
    }

    /**
     * Do network request for [Response] and return [Unit]
     *
     * @receiver [doNetworkRequest]
     */
    protected fun doNetworkRequestUnit(request: suspend () -> Response<Unit>): Flow<Either<NetworkError, Unit>> = doNetworkRequest(request) {
        Either.Right(Unit)
    }

    /**
     * Base function for do network requests
     *
     * @param T data layer model (DTO)
     * @param S domain layer model
     * @param request http request function from api service
     * @param successful handle response body with custom mapping
     *
     * @return [NetworkError] or [Response.body] in [Flow] with [Either]
     *
     * @see [Flow]
     * @see [Either]
     * @see [NetworkError]
     */
    private fun <T, S> doNetworkRequest(request: suspend () -> Response<T>, successful: (T) -> Either.Right<S>) = flow<Either<NetworkError, S>> {
        request().let {
            when
            {
                it.isSuccessful ->
                {
                    emit(successful.invoke(it.body()!!))
                }

                else ->
                {
                    val errorResource = R.string::class.java.getDeclaredField(it.errorBody()!!.string()).getInt(null)
                    emit(Either.Left(NetworkError.Api(errorResource)))
                }
            }
        }
    }.flowOn(Dispatchers.IO).catch { exception ->
        when (exception)
        {
            is InterruptedIOException ->
            {
                emit(Either.Left(NetworkError.Timeout))
            }

            else ->
            {
                val message = exception.localizedMessage
                if (BuildConfig.DEBUG)
                {
                    Log.d(this@BaseRepository.javaClass.simpleName, message ?: "Error")
                }
                emit(Either.Left(NetworkError.Unexpected))
            }
        }
    }

    /**
     * Get non-nullable body from network request
     *
     * &nbsp
     *
     * ## How to use:
     * ```
     * override fun fetchFoo() = doNetworkRequestWithMapping {
     *     service.fetchFoo().onSuccess { data ->
     *         make something with data
     *     }
     * }
     * ```
     * @see Response.body
     * @see let
     */
    protected inline fun <T : Response<S>, S> T.onSuccess(block: (S) -> Unit): T
    {
        this.body()?.let(block)
        return this
    }
}