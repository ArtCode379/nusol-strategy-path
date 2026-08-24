package nusol.management.nusolstrategypath.ui.composable.screen.servicedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import nusol.management.nusolstrategypath.R
import nusol.management.nusolstrategypath.data.model.ServiceModel
import nusol.management.nusolstrategypath.ui.composable.shared.QJCXUContentWrapper
import nusol.management.nusolstrategypath.ui.composable.shared.QJCXUEmptyView
import nusol.management.nusolstrategypath.ui.state.DataUiState
import nusol.management.nusolstrategypath.ui.viewmodel.ServiceDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val serviceState by viewModel.serviceState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeServiceById(serviceId)
    }

    ServiceDetailsContent(
        serviceState = serviceState,
        modifier = modifier,
        onNavigateToCheckout = onNavigateToCheckout
    )
}

@Composable
private fun ServiceDetailsContent(
    serviceState: DataUiState<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        QJCXUContentWrapper<ServiceModel>(
            dataState = serviceState,

            dataPopulated = {
                ServicesDetailsPopulated(
                    service = (serviceState as DataUiState.Populated).data,
                    onNavigateToCheckout = onNavigateToCheckout,
                )
            },

            dataEmpty = {
                QJCXUEmptyView(
                    primaryText = stringResource(R.string.qjcxu_services_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ServicesDetailsPopulated(
    service: ServiceModel,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {

}