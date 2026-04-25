package ru.hse.app.hasslspace.domain.service.common

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.FileNotFoundException

class CropProfilePhotoService : CropService {
    override fun startCrop(
        uri: Uri,
        context: android.content.Context,
        cropLauncher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>
    ) {
        val destinationUri = Uri.fromFile(
            File(
                context.cacheDir,
                "cropped_profile_image_${System.currentTimeMillis()}.jpg"
            )
        )

        val uCrop = UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1080, 1080)
            .withOptions(getCropOptions())

        cropLauncher.launch(uCrop.getIntent(context))
    }

    override fun getCropOptions(): UCrop.Options {
        return UCrop.Options().apply {
            setCompressionQuality(100)
            setHideBottomControls(false)
            setFreeStyleCropEnabled(false)
            setShowCropGrid(false)
            setCircleDimmedLayer(true)
        }
    }

    fun getImageSize(context: Context, uri: Uri): Long {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                if (sizeIndex != -1) {
                    cursor.getLong(sizeIndex)
                } else {
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        inputStream.available().toLong()
                    } ?: 0L
                }
            } ?: 0L
        } catch (e: FileNotFoundException) {
            0L
        } catch (e: SecurityException) {
            0L
        }
    }
}