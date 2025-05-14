package dev.hawk0f.itutor.profile.presentation.ui

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.widgets.CommonScreen

@Composable
fun ProfileScreenWrapper(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val profileState by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    ProfileEffect.Back -> {
                        //navigate back
                    }

                    is ProfileEffect.ShowMessage -> context.showToastLong(effect.message)
                }
            }
        }
    }

    ProfileScreen(
        name = profileState.name,
        surname = profileState.surname,
        phoneNumber = profileState.phoneNumber,
        email = profileState.email,
        isLoading = profileState.isLoading,
        onNameChange = {
            viewModel.sendIntent(ProfileIntent.NameChanged(it))
        },
        onSurnameChange = {
            viewModel.sendIntent(ProfileIntent.SurnameChanged(it))
        },
        onEmailChange = {
            viewModel.sendIntent(ProfileIntent.EmailChanged(it))
        },
        onPhoneNumberChange = {
            viewModel.sendIntent(ProfileIntent.PhoneNumberChanged(it))
        }
    )
}

@Composable
private fun ProfileScreen(
    name: String = "",
    surname: String = "",
    email: String = "",
    phoneNumber: String = "",
    isLoading: Boolean = false,
    onNameChange: (String) -> Unit = {},
    onSurnameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPhoneNumberChange: (String) -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {},
) {
    CommonScreen(
        topAppBarTitle = stringResource(R.string.profile),
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
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                        .background(Color.White, CircleShape),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                    )
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

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

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        onPhoneNumberChange(it)
                    },
                    label = { Text(stringResource(R.string.phoneNumber)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onUpdateClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save_changes),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onLogOutClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.log_out),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}