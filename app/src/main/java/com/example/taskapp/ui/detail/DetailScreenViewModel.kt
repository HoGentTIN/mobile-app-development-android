package com.example.taskapp.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class DetailWizardSteps {
    NAME,
    OCCUPATION,
    LIVING_LOCATION,
    LOVES_ANDROID,
    FILLED_IN,
}

data class DetailData(
    val detailStep: DetailWizardSteps = DetailWizardSteps.NAME,
    val name: String? = null,
    val occupation: String? = null,
    val livingLocation: String? = null,
    val lovesAndroid: Boolean = false,
)

class DetailScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetailData())
    val uiState = _uiState.asStateFlow()

    fun validateName() = !uiState.value.name.isNullOrBlank()
    fun validateOccupation() = !uiState.value.occupation.isNullOrBlank()
    fun validateLivingLocation() = !uiState.value.livingLocation.isNullOrBlank()
    fun validateLovesAndroid() = uiState.value.lovesAndroid == true
    fun setName(name: String) = _uiState.update { it.copy(name = name) }
    fun setOccupation(occupation: String) = _uiState.update { it.copy(occupation = occupation) }
    fun setLivingLocation(livingLocation: String) = _uiState.update { it.copy(livingLocation = livingLocation) }
    fun setLovesAndroid(lovesAndroid: Boolean) = _uiState.update { it.copy(lovesAndroid = lovesAndroid) }

    fun next() {
        when (_uiState.value.detailStep) {
            DetailWizardSteps.NAME -> {
                assert(validateName())
                _uiState.update { it.copy(detailStep = DetailWizardSteps.OCCUPATION) }
            }
            DetailWizardSteps.OCCUPATION -> {
                assert(validateOccupation())
                _uiState.update { it.copy(detailStep = DetailWizardSteps.LIVING_LOCATION) }
            }
            DetailWizardSteps.LIVING_LOCATION -> {
                assert(validateLivingLocation())
                _uiState.update { it.copy(detailStep = DetailWizardSteps.LOVES_ANDROID) }
            }
            DetailWizardSteps.LOVES_ANDROID -> {
                assert(validateLovesAndroid())
                _uiState.update { it.copy(detailStep = DetailWizardSteps.FILLED_IN) }
            }
            DetailWizardSteps.FILLED_IN -> throw IllegalStateException("No next here")
        }
    }

    fun back() {
        when (_uiState.value.detailStep) {
            DetailWizardSteps.NAME ->
                throw IllegalStateException("No back here")
            DetailWizardSteps.OCCUPATION ->
                _uiState.update { it.copy(detailStep = DetailWizardSteps.NAME) }
            DetailWizardSteps.LIVING_LOCATION ->
                _uiState.update { it.copy(detailStep = DetailWizardSteps.OCCUPATION) }
            DetailWizardSteps.LOVES_ANDROID ->
                _uiState.update { it.copy(detailStep = DetailWizardSteps.LIVING_LOCATION) }
            DetailWizardSteps.FILLED_IN ->
                _uiState.update { it.copy(detailStep = DetailWizardSteps.LOVES_ANDROID) }
        }
    }
}
