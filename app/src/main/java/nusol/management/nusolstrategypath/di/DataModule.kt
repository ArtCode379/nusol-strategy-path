package nusol.management.nusolstrategypath.di

import nusol.management.nusolstrategypath.data.repository.BookingRepository
import nusol.management.nusolstrategypath.data.repository.QJCXUOnboardingRepo
import nusol.management.nusolstrategypath.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        QJCXUOnboardingRepo(
            qjcxuOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ServiceRepository() }

    single{
        BookingRepository(
            bookingDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}