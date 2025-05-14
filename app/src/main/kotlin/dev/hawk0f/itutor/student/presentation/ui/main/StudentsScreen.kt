package dev.hawk0f.itutor.student.presentation.ui.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.widgets.CommonScreen
import dev.hawk0f.itutor.student.presentation.models.StudentUI

@Composable
fun StudentsScreenWrapper(
    viewModel: StudentsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val studentsState by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    StudentsEffect.NavigateToAddStudent -> {
                        //navigate to add student screen
                    }

                    is StudentsEffect.NavigateToEditStudent -> {
                        //navigate to edit student screen
                    }

                    is StudentsEffect.ShowMessage -> context.showToastLong(effect.message)
                }
            }
        }
    }

    StudentsScreen(
        students = studentsState.students,
        isLoading = studentsState.isLoading,
        onStudentClick = {
            viewModel.sendIntent(StudentsIntent.StudentClick(it))
        },
        onDeleteStudent = {
            viewModel.sendIntent(StudentsIntent.StudentDeleteClick(it))
        },
    )
}

@Composable
private fun StudentsScreen(
    students: List<StudentUI> = emptyList(),
    isLoading: Boolean = false,
    onStudentClick: (studentId: Int) -> Unit,
    onDeleteStudent: (studentId: Int) -> Unit
) {
    CommonScreen(
        topAppBarTitle = stringResource(R.string.students),
        navIconOverride = null
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            if (students.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_students)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    itemsIndexed(
                        students,
                        key = { index, student -> student.id }
                    ) { index, student ->
                        StudentItem(
                            number = index + 1,
                            student = student,
                            onStudentClick = {
                                onStudentClick(student.id)
                            },
                            onDeleteStudent = {
                                onDeleteStudent(student.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StudentItem(
    number: Int,
    student: StudentUI,
    onStudentClick: () -> Unit,
    onDeleteStudent: () -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDeleteStudent()
                }

                else -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val alignment = when (direction) {
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                else -> Alignment.CenterStart
            }
            val icon = when (direction) {
                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
                else -> null
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) {
                    0.75f
                } else {
                    1f
                },
                label = ""
            )

            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                    SwipeToDismissBoxValue.Settled -> Color.Red
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color),
                contentAlignment = alignment
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.scale(scale),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        },
        content = {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 5.dp
                ),
                onClick = onStudentClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(.1f),
                        text = number.toString(),
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(.9f),
                        text = student.fullName,
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun StudentsScreenPreview() {
    StudentsScreen(
        students = listOf(
            StudentUI(
                id = 0,
                fullName = "Vasya Ivanov",
                age = 0,
                phoneNumber = "",
                singlePrice = 0.0,
                groupPrice = 0.0,
                note = "",
                userId = CurrentUser.getUserId(),
            ),
            StudentUI(
                id = 1,
                fullName = "Nikita Popov",
                age = 0,
                phoneNumber = "",
                singlePrice = 0.0,
                groupPrice = 0.0,
                note = "",
                userId = CurrentUser.getUserId(),
            ),
            StudentUI(
                id = 2,
                fullName = "Ivan Lobov",
                age = 0,
                phoneNumber = "",
                singlePrice = 0.0,
                groupPrice = 0.0,
                note = "",
                userId = CurrentUser.getUserId(),
            )
        ),
        onStudentClick = {},
        onDeleteStudent = {}
    )
}