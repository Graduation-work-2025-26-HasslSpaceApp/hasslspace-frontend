package ru.hse.app.androidApp.ui.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    @Singleton
    fun provideErrorHandler(
        @ApplicationContext context: Context,
    ): ErrorHandler {
        return ErrorHandler(context)
    }
}