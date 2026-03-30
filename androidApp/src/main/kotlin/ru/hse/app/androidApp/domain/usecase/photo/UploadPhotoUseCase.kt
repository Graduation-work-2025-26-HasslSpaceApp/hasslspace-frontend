package ru.hse.app.androidApp.domain.usecase.photo

import android.net.Uri
import coil3.ImageLoader
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.domain.repository.UserRepository
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService

class UploadPhotoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val imageLoader: ImageLoader,
    private val photoConverterService: PhotoConverterService,
) {
    suspend operator fun invoke(
        imageUri: Uri?,
        photoUrl: String?
    ): Result<String> {
        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()

        val userPhotoMultipart = imageUri?.let { photoConverterService.uriToMultipart(it) }

        userPhotoMultipart?.let { multipart ->
            val typePart = "user".toRequestBody("text/plain".toMediaType())
            val photoUrlPart = photoUrl
                ?.takeIf { it.isNotBlank() }
                ?.toRequestBody("text/plain".toMediaType())

            return userRepository.uploadPhoto(multipart, typePart, photoUrlPart)
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