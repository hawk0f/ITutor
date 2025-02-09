package dev.hawk0f.itutor.core.data.utils

import kotlinx.serialization.json.Json

internal val jsonClient = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}