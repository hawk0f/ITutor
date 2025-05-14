package dev.hawk0f.itutor.core.presentation.validation

interface Validator {
    operator fun invoke(text: String?): ValidationResult
}