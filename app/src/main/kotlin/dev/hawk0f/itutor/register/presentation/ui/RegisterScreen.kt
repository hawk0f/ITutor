package dev.hawk0f.itutor.register.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.EventManager
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.widgets.CommonScreen
import dev.hawk0f.itutor.core.presentation.widgets.PasswordTextField

@Composable
fun RegisterScreenWrapper(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val authState by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    RegEffect.Back -> {
                        //navigate back
                    }

                    RegEffect.NavigateToMain -> {
                        //navigate to main screen
                    }

                    is RegEffect.ShowMessage -> context.showToastLong(effect.message)
                }
            }
        }
    }

    RegScreen(
        name = authState.name,
        surname = authState.surname,
        email = authState.email,
        password = authState.password,
        isLoading = authState.isLoading,
        onEmailChange = {
            viewModel.sendIntent(RegIntent.EmailChanged(it))
        },
        onPasswordChange = {
            viewModel.sendIntent(RegIntent.PasswordChanged(it))
        },
        onNameChange = {
            viewModel.sendIntent(RegIntent.NameChanged(it))
        },
        onSurnameChange = {
            viewModel.sendIntent(RegIntent.SurnameChanged(it))
        }
    )
}

@Composable
private fun RegScreen(
    name: String = "",
    surname: String = "",
    email: String = "",
    password: String = "",
    isLoading: Boolean = false,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onSurnameChange: (String) -> Unit = {},
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    CommonScreen(
        topAppBarTitle = stringResource(R.string.reg),
        navIconOverride = null,
        actions = null,
        showBottomNav = false,
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

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        onEmailChange(it)
                    },
                    label = { Text(stringResource(R.string.email)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = password,
                    onValueChange = {
                        onPasswordChange(it)
                    },
                    label = stringResource(R.string.password),
                    placeholder = stringResource(R.string.password),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSignInClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        onSignUpClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.back_to_auth),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegScreenPreview() {
    RegScreen(
        name = "Vasya",
        surname = "Pupkin",
        email = "email@mail.ru",
        password = "12345",
        isLoading = false,
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {},
        onSignUpClick = {}
    )
}