package dev.hawk0f.itutor.core.presentation.extensions

/**
 * Cut the text to the [limit] amount of symbols and add ... if the length is more than limit
 */
fun String.takeDot(limit: Int) = if (this.length <= limit) this else this.take(limit) + "..."