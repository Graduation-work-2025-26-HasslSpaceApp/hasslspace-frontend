package ru.hse.app.androidApp.domain.di

import android.content.Context
import android.content.SharedPreferences
import coil3.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideLocationService(@ApplicationContext context: Context): LocationService {
//        return LocationService(context)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUUIDService(): UuidService {
//        return UuidService()
//    }
//
//    @Provides
//    @Singleton
//    fun provideSafeApiCaller(): SafeApiCaller {
//        return SafeApiCaller()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTrackingService(): TrackingService {
//        return TrackingService()
//    }

//    @Provides
//    @Singleton
//    fun provideErrorHandler(
//        @ApplicationContext context: Context,
//        verificationManager: VerificationManager
//    ): ErrorHandler {
//        return ErrorHandler(context, verificationManager)
//    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideToastManager(@ApplicationContext context: Context): ToastManager {
        return ToastManager(context)
    }

    @Provides
    @Singleton
    fun provideCropProfilePhotoService(): CropProfilePhotoService {
        return CropProfilePhotoService()
    }
//
//    @Provides
//    @Singleton
//    fun provideCropRoutePreviewService(): CropRoutePreviewService {
//        return CropRoutePreviewService()
//    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
}
