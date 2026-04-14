package ru.hse.app.androidApp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.hse.app.androidApp.data.centrifugo.CentrifugeService
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.data.repository.CallRepositoryImpl
import ru.hse.app.androidApp.data.repository.ChannelRepositoryImpl
import ru.hse.app.androidApp.data.repository.ChatRepositoryImpl
import ru.hse.app.androidApp.data.repository.FriendRepositoryImpl
import ru.hse.app.androidApp.data.repository.InvitationRepositoryImpl
import ru.hse.app.androidApp.data.repository.RoleRepositoryImpl
import ru.hse.app.androidApp.data.repository.ServerRepositoryImpl
import ru.hse.app.androidApp.data.repository.UserRepositoryImpl
import ru.hse.app.androidApp.data.roomstorage.AppDatabase
import ru.hse.app.androidApp.data.roomstorage.ChatDao
import ru.hse.app.androidApp.domain.repository.CallRepository
import ru.hse.app.androidApp.domain.repository.ChannelRepository
import ru.hse.app.androidApp.domain.repository.ChatRepository
import ru.hse.app.androidApp.domain.repository.FriendRepository
import ru.hse.app.androidApp.domain.repository.InvitationRepository
import ru.hse.app.androidApp.domain.repository.RoleRepository
import ru.hse.app.androidApp.domain.repository.ServerRepository
import ru.hse.app.androidApp.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

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
    fun provideInvitationRepository(
        apiService: ApiService,
        apiCaller: ApiCaller
    ): InvitationRepository {
        return InvitationRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideCallRepository(apiService: ApiService, apiCaller: ApiCaller): CallRepository {
        return CallRepositoryImpl(apiService, apiCaller)
    }

    @Provides
    fun provideChatRepository(
        apiService: ApiService,
        apiCaller: ApiCaller,
        centrifugoService: CentrifugeService,
        chatDao: ChatDao,
        coroutineScope: CoroutineScope
    ): ChatRepository {
        return ChatRepositoryImpl(apiService, apiCaller, centrifugoService, chatDao, coroutineScope)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideChatDao(database: AppDatabase): ChatDao = database.messageDao()
}
