package ru.hse.app.androidApp.domain.service.common

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class PhotoConverterService @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun urlToUri(url: String?): Uri? {
        return try {
            url?.let { Uri.parse(it) }
        } catch (e: Exception) {
            return null
        }
    }

    fun uriToMultipart(uri: Uri): MultipartBody.Part? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.use { stream ->
                val file = File.createTempFile("temp_", null, context.cacheDir)
                file.outputStream().use { output ->
                    stream.copyTo(output)
                }
                val mimeType = getMimeType(uri) ?: "image/jpeg"

                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData("photo", file.name, requestFile)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getMimeType(uri: Uri): String? {
        return context.contentResolver.getType(uri)
            ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString())?.let { ext ->
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.lowercase())
            }
    }


}