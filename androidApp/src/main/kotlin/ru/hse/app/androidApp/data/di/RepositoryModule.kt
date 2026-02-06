package ru.hse.app.androidApp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.data.repository.UserRepositoryImpl
import ru.hse.app.androidApp.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(apiService: ApiService, apiCaller: ApiCaller): UserRepository {
        return UserRepositoryImpl(apiService,apiCaller)
    }
}
