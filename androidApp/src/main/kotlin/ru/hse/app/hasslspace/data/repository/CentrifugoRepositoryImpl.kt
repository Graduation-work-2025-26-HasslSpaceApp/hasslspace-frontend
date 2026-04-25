package ru.hse.app.hasslspace.data.repository

import ru.hse.app.hasslspace.data.network.ApiCaller
import ru.hse.app.hasslspace.data.network.ApiService
import ru.hse.app.hasslspace.domain.repository.CentrifugoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CentrifugoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller,
) : CentrifugoRepository {

    override suspend fun getCentrifugoToken(): Result<String> {
        return apiCaller.safeApiCall { apiService.getTokenForCentrifugo() }
    }
}
