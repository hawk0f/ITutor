package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator
import javax.inject.Inject

class ValidateName @Inject constructor(@ApplicationContext private val context: Context) : Validator
{

    override operator fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, context.getString(R.string.field_must_be_filled))
            }

            !text.matches(Regex("^[\\p{L} ]+$")) ->
            {
                ValidationResult(isSuccessful = false, context.getString(R.string.incorrect_name))
            }

            text.length < 2 ->
            {
                ValidationResult(isSuccessful = false, context.getString(R.string.name_must_contain_at_least_2_characters))
            }

            else ->
            {
                ValidationResult(
                    isSuccessful = true,
                )
            }
        }
    }
}