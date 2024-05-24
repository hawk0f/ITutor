package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import android.util.Patterns
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator
import javax.inject.Inject

class ValidateAge @Inject constructor(@ApplicationContext private val context: Context) : Validator
{
    override fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.field_must_be_filled))
            }

            text.toInt() <= 4 || text.toInt() >= 100 ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.invalid_age))
            }

            else ->
            {
                ValidationResult(isSuccessful = true)
            }
        }
    }

}