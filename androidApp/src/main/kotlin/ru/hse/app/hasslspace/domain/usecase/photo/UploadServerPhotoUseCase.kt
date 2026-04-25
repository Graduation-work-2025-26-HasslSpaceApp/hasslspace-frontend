package ru.hse.app.hasslspace.domain.usecase.photo

import android.net.Uri
import coil3.ImageLoader
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.domain.repository.ServerRepository
import ru.hse.app.hasslspace.domain.service.common.FileConverterService

class UploadServerPhotoUseCase @Inject constructor(
    private val serverRepository: ServerRepository,
    private val imageLoader: ImageLoader,
    private val fileConverterService: FileConverterService,
) {
    suspend operator fun invoke(
        imageUri: Uri?,
        photoUrl: String?
    ): Result<String> {

        val userPhotoMultipart = imageUri?.let { fileConverterService.uriToMultipart(it) }

        userPhotoMultipart?.let { multipart ->
            val typePart = "user".toRequestBody("text/plain".toMediaType())
            val photoUrlPart = photoUrl
                ?.takeIf { it.isNotBlank() }
                ?.toRequestBody("text/plain".toMediaType())

            val result = serverRepository.uploadPhoto(multipart, photoUrlPart)

            imageLoader.memoryCache?.clear()
            imageLoader.diskCache?.clear()

            return result
        }

        return Result.failure(
            ApiException(
                null,
                ApiException.PHOTO_UPLOADING_ERROR + " multipart is null",
                null
            )
        )
    }
}