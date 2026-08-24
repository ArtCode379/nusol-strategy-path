package nusol.management.nusolstrategypath.data.repository

import nusol.management.nusolstrategypath.data.datastore.QJCXUOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QJCXUOnboardingRepo(
    private val qjcxuOnboardingStoreManager: QJCXUOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return qjcxuOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            qjcxuOnboardingStoreManager.setOnboardedState(state)
        }
    }
}