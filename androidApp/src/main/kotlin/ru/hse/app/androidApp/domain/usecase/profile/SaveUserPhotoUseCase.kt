package ru.hse.app.androidApp.domain.usecase.profile

import android.net.Uri
import coil3.ImageLoader
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.app.androidApp.data.exception.DataException
import ru.hse.app.androidApp.domain.repository.UserRepository
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService

class SaveUserPhotoUseCase @Inject constructor(
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

            val savePhotoResult = userRepository.uploadPhoto(multipart, typePart, photoUrlPart)

            savePhotoResult.fold(
                onSuccess = {
                    return userRepository.saveUserPhoto(it)
                },
                onFailure = {
                    return Result.failure(
                        savePhotoResult.exceptionOrNull()
                            ?: DataException(DataException.PHOTO_UPLOADING_ERROR)
                    )
                }
            )
        }

        return Result.failure(DataException(DataException.PHOTO_UPLOADING_ERROR))
    }
}