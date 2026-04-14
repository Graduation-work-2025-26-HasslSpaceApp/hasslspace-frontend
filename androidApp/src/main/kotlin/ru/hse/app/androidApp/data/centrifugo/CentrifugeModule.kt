package ru.hse.app.androidApp.data.centrifugo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CentrifugeModule {

    @Binds
    @Singleton
    abstract fun bindCentrifugeService(
        impl: CentrifugeServiceImpl
    ): CentrifugeService
}