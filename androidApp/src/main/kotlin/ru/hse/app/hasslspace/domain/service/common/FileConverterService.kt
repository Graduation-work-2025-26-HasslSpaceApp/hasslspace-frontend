package ru.hse.app.hasslspace.domain.service.common

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class FileConverterService @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun urlToUri(url: String?): Uri? {
        return try {
            url?.let { Uri.parse(it) }
        } catch (e: Exception) {
            return null
        }
    }

    fun uriToMultipart(uri: Uri, formData: String = "photo"): MultipartBody.Part? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.use { stream ->
                val mimeType = getMimeType(uri) ?: "image/jpeg"
                val extension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(mimeType) ?: "jpg"
                val fileName = getOriginalFileName(uri, extension)

                val file = File(context.cacheDir, fileName)
                file.outputStream().use { output ->
                    stream.copyTo(output)
                }

                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData(formData, fileName, requestFile)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getOriginalFileName(uri: Uri, fallbackExtension: String): String {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    val name = cursor.getString(nameIndex)
                    if (!name.isNullOrBlank()) return name
                }
            }
        }

        uri.lastPathSegment
            ?.takeIf { it.contains('.') }
            ?.let { return it }

        return "file_${System.currentTimeMillis()}.$fallbackExtension"
    }

    fun isPhoto(uri: Uri): Boolean {
        val mimeType = getMimeType(uri)
        return mimeType?.startsWith("image/") == true
    }

    private fun getMimeType(uri: Uri): String? {
        return context.contentResolver.getType(uri)
            ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString())?.let { ext ->
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.lowercase())
            }
    }

    fun getFileSize(uri: Uri): Long? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.available().toLong()
            }
        } catch (e: Exception) {
            null
        }
    }

}