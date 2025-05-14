package dev.hawk0f.itutor.core.presentation.validation

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null,
)