package dev.hawk0f.core.presentation.validation

interface Validator
{
    operator fun invoke(text: String): ValidationResult
}