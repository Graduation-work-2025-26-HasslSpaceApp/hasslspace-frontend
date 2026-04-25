package ru.hse.app.hasslspace.domain.usecase.photo

import android.net.Uri
import coil3.ImageLoader
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.domain.repository.UserRepository
import ru.hse.app.hasslspace.domain.service.common.FileConverterService

class UploadPhotoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val imageLoader: ImageLoader,
    private val fileConverterService: FileConverterService,
) {
    suspend operator fun invoke(
        imageUri: Uri?,
        photoUrl: String?
    ): Result<String> {
        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()

        val userPhotoMultipart = imageUri?.let { fileConverterService.uriToMultipart(it) }

        userPhotoMultipart?.let { multipart ->
            val typePart = "user".toRequestBody("text/plain".toMediaType())
            val photoUrlPart = photoUrl
                ?.takeIf { it.isNotBlank() }
                ?.toRequestBody("text/plain".toMediaType())

            val result = userRepository.uploadPhoto(multipart, typePart, photoUrlPart)

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