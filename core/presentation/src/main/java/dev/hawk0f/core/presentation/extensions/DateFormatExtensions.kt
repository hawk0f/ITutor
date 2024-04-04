package dev.hawk0f.core.presentation.extensions

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.parseToFormat(formatterPattern: String): String {
    val formatter = SimpleDateFormat(formatterPattern, Locale.getDefault())
    return formatter.format(this)
}

fun String.parseToFormat(parserPattern: String, formatterPattern: String): String {
    val parser = SimpleDateFormat(parserPattern, Locale.getDefault())
    val formatter = SimpleDateFormat(formatterPattern, Locale.getDefault())
    return formatter.format(parser.parse(this)!!)
}