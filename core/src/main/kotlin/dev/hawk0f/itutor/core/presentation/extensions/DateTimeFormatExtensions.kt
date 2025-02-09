package dev.hawk0f.itutor.core.presentation.extensions

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.parseToFormat(formatterPattern: String): String
{
    val formatter = DateTimeFormatter.ofPattern(formatterPattern, Locale.getDefault())
    return this.format(formatter)
}

fun LocalTime.parseToFormat(formatterPattern: String): String
{
    val formatter = DateTimeFormatter.ofPattern(formatterPattern, Locale.getDefault())
    return this.format(formatter)
}

fun String.parseToDate(parserPattern: String): LocalDate
{
    val parser = DateTimeFormatter.ofPattern(parserPattern, Locale.getDefault())
    return LocalDate.parse(this, parser)
}

fun String.parseToTime(parserPattern: String): LocalTime
{
    val parser = DateTimeFormatter.ofPattern(parserPattern, Locale.getDefault())
    return LocalTime.parse(this, parser)
}