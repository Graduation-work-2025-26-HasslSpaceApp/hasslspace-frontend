package ru.hse.app.hasslspace.domain.di

import android.content.Context
import android.content.SharedPreferences
import coil3.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hse.app.hasslspace.domain.service.common.ColorService
import ru.hse.app.hasslspace.domain.service.common.CropProfilePhotoService
import ru.hse.app.hasslspace.domain.service.common.ImageCacheManager
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Provides
    @Singleton
    fun provideImageCacheManager(
        imageLoader: ImageLoader
    ): ImageCacheManager = ImageCacheManager(imageLoader)

    @Provides
    @Singleton
    fun provideColorService(): ColorService = ColorService()

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
}
