package com.alish.boilerplate.presentation.core.validation

import dev.hawk0f.itutor.core.presentation.validation.ValidationResult

interface Validator {
    operator fun invoke(text: String): ValidationResult
}