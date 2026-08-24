package nusol.management.nusolstrategypath.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nusol.management.nusolstrategypath.data.model.ServiceModel
import nusol.management.nusolstrategypath.data.repository.ServiceRepository
import nusol.management.nusolstrategypath.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiceDetailsViewModel(
    private val serviceRepository: ServiceRepository,
) : ViewModel() {
    private val _serviceState = MutableStateFlow<DataUiState<ServiceModel>>(DataUiState.Initial)
    val serviceState: StateFlow<DataUiState<ServiceModel>>
        get() = _serviceState.asStateFlow()

    fun observeServiceById(id: Int){
        viewModelScope.launch {
            serviceRepository.observeById(id).collect { service ->
                _serviceState.update {
                    DataUiState.from(service)
                }
            }
        }
    }
}