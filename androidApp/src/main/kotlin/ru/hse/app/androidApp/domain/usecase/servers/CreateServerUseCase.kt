package ru.hse.app.androidApp.domain.usecase.servers

import android.net.Uri
import coil3.ImageLoader
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.repository.ServerRepository
import ru.hse.app.androidApp.domain.repository.UserRepository
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.photo.UploadPhotoUseCase
import javax.inject.Inject

class CreateServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository,
    private val uploadPhotoUseCase: UploadPhotoUseCase,
) {
    suspend operator fun invoke(
        serverName: String,
        imageUri: Uri?
    ): Result<String> {
        if (imageUri == null) {
            val data = CreateServer(name = serverName)
            return serverRepository.createServer(data)
        }

        val uploadResult = uploadPhotoUseCase.invoke(imageUri, null)
        uploadResult.fold(
            onSuccess = {
                val data = CreateServer(name = serverName, photoUrl = it)
                return serverRepository.createServer(data)
            },
            onFailure = {
                return Result.failure(
                    uploadResult.exceptionOrNull()
                        ?: ApiException(null, ApiException.PHOTO_UPLOADING_ERROR, null)
                )
            }
        )
    }
}