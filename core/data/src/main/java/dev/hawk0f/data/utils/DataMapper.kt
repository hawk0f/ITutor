package dev.hawk0f.data.utils

/**
 * Base mapper interface
 *
 * @param T domain layer model
 */
interface DataMapper<T> {

    /**
     * Function for map data layer model to domain layer model
     */
    fun toDomain(): T
}