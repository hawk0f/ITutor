package dev.hawk0f.itutor.auth.presentation.ui

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.extensions.launchWithRepeatOnLifecycle
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.widgets.CommonScreen
import dev.hawk0f.itutor.core.presentation.widgets.PasswordTextField

@Composable
fun AuthScreenWrapper(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val authState by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.launchWithRepeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    AuthEffect.Exit -> {
                        //back press
                    }

                    AuthEffect.NavigateToMain -> {
                        //navigate to main screen
                    }

                    AuthEffect.NavigateToReg -> {
                        //navigate to reg screen
                    }

                    is AuthEffect.ShowMessage -> context.showToastLong(effect.message)
                }
            }
        }
    }

    AuthScreen(
        email = authState.email,
        password = authState.password,
        isLoading = authState.isLoading,
        onEmailChange = {
            viewModel.sendIntent(AuthIntent.EmailChanged(it))
        },
        onPasswordChange = {
            viewModel.sendIntent(AuthIntent.PasswordChanged(it))
        },
        onSignInClick = {
            viewModel.sendIntent(AuthIntent.AuthButton)
        },
        onSignUpClick = {
            viewModel.sendIntent(AuthIntent.RegButton)
        }
    )
}

@Composable
private fun AuthScreen(
    email: String = "",
    password: String = "",
    isLoading: Boolean = false,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    CommonScreen(
        topAppBarTitle = stringResource(R.string.auth),
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
                        text = stringResource(R.string.sign_in),
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
                        text = stringResource(R.string.go_to_sign_up),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        email = "email@mail.ru",
        password = "12345",
        isLoading = false,
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {},
        onSignUpClick = {}
    )
}