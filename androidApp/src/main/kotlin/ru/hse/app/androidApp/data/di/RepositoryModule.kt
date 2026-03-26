package ru.hse.app.androidApp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.data.repository.ChannelRepositoryImpl
import ru.hse.app.androidApp.data.repository.FriendRepositoryImpl
import ru.hse.app.androidApp.data.repository.InvitationRepositoryImpl
import ru.hse.app.androidApp.data.repository.RoleRepositoryImpl
import ru.hse.app.androidApp.data.repository.ServerRepositoryImpl
import ru.hse.app.androidApp.data.repository.UserRepositoryImpl
import ru.hse.app.androidApp.domain.repository.ChannelRepository
import ru.hse.app.androidApp.domain.repository.FriendRepository
import ru.hse.app.androidApp.domain.repository.InvitationRepository
import ru.hse.app.androidApp.domain.repository.RoleRepository
import ru.hse.app.androidApp.domain.repository.ServerRepository
import ru.hse.app.androidApp.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(apiService: ApiService, apiCaller: ApiCaller): UserRepository {
        return UserRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideFriendRepository(apiService: ApiService, apiCaller: ApiCaller): FriendRepository {
        return FriendRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideServerRepository(apiService: ApiService, apiCaller: ApiCaller): ServerRepository {
        return ServerRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideChannelRepository(apiService: ApiService, apiCaller: ApiCaller): ChannelRepository {
        return ChannelRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideRoleRepository(apiService: ApiService, apiCaller: ApiCaller): RoleRepository {
        return RoleRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideInvitationRepository(apiService: ApiService, apiCaller: ApiCaller): InvitationRepository {
        return InvitationRepositoryImpl(apiService, apiCaller)
    }
}
