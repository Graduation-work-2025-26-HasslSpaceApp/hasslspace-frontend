package ru.hse.app.androidApp.domain.service.common

import android.content.Context
import android.util.Log
import coil3.ImageLoader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageCacheManager @Inject constructor(
    private val imageLoader: ImageLoader
) {
    
    fun clearAllCache(context: Context) {
        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()

        Log.d("ImageCacheManager", "Disk cache cleared")
    }
}