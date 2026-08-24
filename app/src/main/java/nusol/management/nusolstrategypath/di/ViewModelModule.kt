package nusol.management.nusolstrategypath.di

import nusol.management.nusolstrategypath.ui.viewmodel.BookingViewModel
import nusol.management.nusolstrategypath.ui.viewmodel.CheckoutViewModel
import nusol.management.nusolstrategypath.ui.viewmodel.QJCXUOnboardingVM
import nusol.management.nusolstrategypath.ui.viewmodel.ServiceDetailsViewModel
import nusol.management.nusolstrategypath.ui.viewmodel.ServiceViewModel
import nusol.management.nusolstrategypath.ui.viewmodel.QJCXUSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        QJCXUSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        QJCXUOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ServiceViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        ServiceDetailsViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        BookingViewModel(
            bookingRepository = get(),
            serviceRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            bookingRepository = get(),
        )
    }
}