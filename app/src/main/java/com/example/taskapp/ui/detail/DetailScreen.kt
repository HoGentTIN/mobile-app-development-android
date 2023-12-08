package com.example.taskapp.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskapp.R

@Composable
fun DetailScreen() {
    val vm: DetailScreenViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()

    BackHandler(
        onBack = { vm.back() },
    )

    when (uiState.detailStep) {
        DetailWizardSteps.NAME -> {
            InputText(
                title = R.string.detail_enter_name,
                value = uiState.name,
                onValueChange = { vm.setName(it) },
                next = { vm.next() },
                isValid = vm.validateName(),
            )
        }

        DetailWizardSteps.OCCUPATION -> {
            InputText(
                title = R.string.detail_enter_occupation,
                value = uiState.occupation,
                onValueChange = { vm.setOccupation(it) },
                next = { vm.next() },
                isValid = vm.validateOccupation(),
            )
        }
        DetailWizardSteps.LIVING_LOCATION -> {
            InputText(
                title = R.string.detail_enter_living_location,
                value = uiState.livingLocation,
                onValueChange = { vm.setLivingLocation(it) },
                next = { vm.next() },
                isValid = vm.validateLivingLocation(),
            )
        }
        DetailWizardSteps.LOVES_ANDROID -> {
            InputStep(
                title = R.string.detail_enter_loves_android,
                next = { vm.next() },
                canNext = vm.validateLovesAndroid(),
            ) {
                TextRadioButton(
                    text = R.string.detail_option_loves_android,
                    selected = uiState.lovesAndroid,
                    onClick = { vm.setLovesAndroid(true) },
                )
                TextRadioButton(
                    text = R.string.detail_option_dislikes_android,
                    selected = !uiState.lovesAndroid,
                    onClick = { vm.setLovesAndroid(false) },
                )
            }
        }
        DetailWizardSteps.FILLED_IN -> {
            Column(Modifier.padding(dimensionResource(id = R.dimen.largePadding))) {
                Text(
                    text = stringResource(R.string.detail_name_label, uiState.name!!),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.mediumSpacer)))
                Text(
                    text = stringResource(R.string.detail_occupation_label, uiState.occupation!!),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.mediumSpacer)))
                Text(
                    text = stringResource(R.string.detail_living_location_label, uiState.livingLocation!!),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.mediumSpacer)))
                Text(
                    text = stringResource(R.string.detail_loves_android),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

@Composable
fun TextRadioButton(text: Int, selected: Boolean, onClick: () -> Unit) {
    // source: https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#RadioButton(kotlin.Boolean,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Boolean,androidx.compose.material3.RadioButtonColors,androidx.compose.foundation.interaction.MutableInteractionSource)
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton,
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null, // null recommended for accessibility with screenreaders
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputText(
    title: Int,
    value: String?,
    onValueChange: (String) -> Unit,
    next: () -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier,
) {
    val isError = !(value == null || isValid)
    InputStep(title, value != null && isValid, next, modifier) {
        TextField(
            value = value ?: "",
            onValueChange = { onValueChange(it) },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(stringResource(R.string.detail_value_required_error))
                }
            },
        )
    }
}

@Composable
private fun InputStep(
    title: Int,
    canNext: Boolean,
    next: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Column(modifier = modifier.padding(dimensionResource(R.dimen.mediumPadding))) {
            Text(text = stringResource(title))
            content()
            Row {
                Spacer(Modifier.weight(1F))
                Button(onClick = next, enabled = canNext) {
                    Text("next")
                }
            }
        }
    }
}
