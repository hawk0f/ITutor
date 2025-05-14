package dev.hawk0f.itutor.student.presentation.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.widgets.CommonScreen
import dev.hawk0f.itutor.core.presentation.widgets.NumberTextField

@Composable
fun EditStudentScreenWrapper(
    viewModel: EditStudentViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val addStudentState by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    EditStudentEffect.BackToMain -> {
                        //navigate to main screen
                    }

                    is EditStudentEffect.ShowMessage -> context.showToastLong(effect.message)
                }
            }
        }
    }

    EditStudentScreen(
        name = addStudentState.name,
        surname = addStudentState.surname,
        age = addStudentState.age,
        singlePrice = addStudentState.singlePrice,
        groupPrice = addStudentState.groupPrice,
        phoneNumber = addStudentState.phoneNumber,
        note = addStudentState.note,
        isLoading = addStudentState.isLoading,
        onNameChange = {
            viewModel.sendIntent(EditStudentIntent.NameChanged(it))
        },
        onSurnameChange = {
            viewModel.sendIntent(EditStudentIntent.SurnameChanged(it))
        },
        onAgeChange = {
            viewModel.sendIntent(EditStudentIntent.AgeChanged(it))
        },
        onSinglePriceChange = {
            viewModel.sendIntent(EditStudentIntent.SinglePriceChanged(it))
        },
        onGroupPriceChange = {
            viewModel.sendIntent(EditStudentIntent.GroupPriceChanged(it))
        },
        onPhoneNumberChange = {
            viewModel.sendIntent(EditStudentIntent.PhoneNumberChanged(it))
        },
        onNoteChange = {
            viewModel.sendIntent(EditStudentIntent.NoteChanged(it))
        },
        onUpdateStudent = {
            viewModel.sendIntent(EditStudentIntent.UpdateButtonClick)
        }
    )
}

@Composable
private fun EditStudentScreen(
    name: String = "",
    surname: String = "",
    age: Int = 0,
    singlePrice: Double = 0.0,
    groupPrice: Double = 0.0,
    phoneNumber: String = "",
    note: String = "",
    isLoading: Boolean = false,
    onNameChange: (String) -> Unit = {},
    onSurnameChange: (String) -> Unit = {},
    onAgeChange: (Int) -> Unit = {},
    onSinglePriceChange: (Double) -> Unit = {},
    onGroupPriceChange: (Double) -> Unit = {},
    onPhoneNumberChange: (String) -> Unit = {},
    onNoteChange: (String) -> Unit = {},
    onUpdateStudent: () -> Unit = {},
) {
    CommonScreen(
        topAppBarTitle = stringResource(R.string.editing_student)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        onNameChange(it)
                    },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = surname,
                    onValueChange = {
                        onSurnameChange(it)
                    },
                    label = { Text(stringResource(R.string.surname)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                NumberTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = age,
                    onValueChange = {
                        onAgeChange(it)
                    },
                    label = stringResource(R.string.age),
                    placeholder = stringResource(R.string.age)
                )

                Spacer(modifier = Modifier.height(8.dp))

                NumberTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = singlePrice.toInt(),
                    onValueChange = {
                        onSinglePriceChange(it.toDouble())
                    },
                    label = stringResource(R.string.singlePrice),
                    placeholder = stringResource(R.string.singlePrice),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                NumberTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = groupPrice.toInt(),
                    onValueChange = {
                        onGroupPriceChange(it.toDouble())
                    },
                    label = stringResource(R.string.groupPrice),
                    placeholder = stringResource(R.string.groupPrice),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        onPhoneNumberChange(it)
                    },
                    label = { Text(stringResource(R.string.phoneNumber)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = {
                        onNoteChange(it)
                    },
                    label = { Text(stringResource(R.string.note)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onUpdateStudent()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save_changes),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditStudentScreenPreview() {
    EditStudentScreen(
        name = "Vasya",
        surname = "Popov",
        age = 10,
        singlePrice = 1000.0,
        groupPrice = 500.0,
        note = "",
    )
}