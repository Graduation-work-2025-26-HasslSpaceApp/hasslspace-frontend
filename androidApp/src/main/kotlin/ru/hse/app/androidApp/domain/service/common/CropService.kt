package ru.hse.app.androidApp.domain.service.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.yalantis.ucrop.UCrop

interface CropService {
    fun startCrop(
        uri: Uri,
        context: Context,
        cropLauncher: ActivityResultLauncher<Intent>
    )

    fun getCropOptions(): UCrop.Options
}