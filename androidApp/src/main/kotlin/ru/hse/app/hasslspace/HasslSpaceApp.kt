package ru.hse.app.hasslspace

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import dagger.hilt.android.HiltAndroidApp
import ru.hse.app.hasslspace.domain.service.common.ImageCacheManager
import javax.inject.Inject

@HiltAndroidApp
class HasslSpaceApp : Application() {
    @Inject
    lateinit var imageCacheManager: ImageCacheManager

    override fun onCreate() {
        super.onCreate()

        imageCacheManager.clearAllCache(this)

//        if (isMainProcess()) {
//            MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
//            MapKitFactory.initialize(this)
//        }
//
//        val config = AppMetricaConfig
//            .newConfigBuilder(BuildConfig.APPMETRICA_API_KEY)
//            .withLocationTracking(true)
//            .build()
//        AppMetrica.activate(this, config)
//        AppMetrica.enableActivityAutoTracking(this)
    }

    private fun isMainProcess(): Boolean {
        val pid = Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val currentProcessName = activityManager.runningAppProcesses
            ?.firstOrNull { it.pid == pid }
            ?.processName

        return packageName == currentProcessName
    }
}