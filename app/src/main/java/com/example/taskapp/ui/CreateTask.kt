package com.example.taskapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.taskapp.R
import com.example.taskapp.ui.theme.TaskAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(
    taskName: String,
    taskDescription: String,
    onTaskNameChanged: (String) -> Unit,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskSaved: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(R.dimen.cardCornerRadius)),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.mediumPadding)),
            ) {
                OutlinedTextField(
                    value = taskName,
                    onValueChange = onTaskNameChanged,
                    label = { Text("taskname") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = onTaskDescriptionChanged,
                    label = { Text("description") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
                Row {
                    Spacer(Modifier.weight(1F))
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.smallSpacer)))
                    TextButton(onClick = onTaskSaved) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateTaskPreview() {
    TaskAppTheme {
        CreateTask("todo", "descr", {}, {}, {}, { /* on dismiss */ })
    }
}
