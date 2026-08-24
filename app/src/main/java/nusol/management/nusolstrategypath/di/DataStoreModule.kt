package nusol.management.nusolstrategypath.di

import nusol.management.nusolstrategypath.data.datastore.QJCXUOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { QJCXUOnboardingPrefs(androidContext()) }
}