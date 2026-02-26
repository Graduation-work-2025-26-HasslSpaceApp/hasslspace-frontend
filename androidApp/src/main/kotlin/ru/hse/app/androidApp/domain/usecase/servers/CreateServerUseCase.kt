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
import javax.inject.Inject

class CreateServerUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
    private val imageLoader: ImageLoader,
    private val photoConverterService: PhotoConverterService,
) {
    suspend operator fun invoke(
        serverName: String,
        imageUri: Uri?
    ): Result<String> {
        if (imageUri == null) {
            val data = CreateServer(name = serverName)
            return serverRepository.createServer(data)
        }

        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()

        val userPhotoMultipart = imageUri.let { photoConverterService.uriToMultipart(it) }


        userPhotoMultipart?.let { multipart ->
            val typePart = "user".toRequestBody("text/plain".toMediaType())

            val savePhotoResult = userRepository.uploadPhoto(multipart, typePart, null)

            savePhotoResult.fold(
                onSuccess = {
                    val data = CreateServer(name = serverName, photoUrl = it)
                    return serverRepository.createServer(data) //TODO попросить сашу возвращать ссылку на фото
                },
                onFailure = {
                    return Result.failure(
                        savePhotoResult.exceptionOrNull()
                        //TODO во всех юз кейсах сделать преобразование ошибки апи в ошибку UI
                            ?: ApiException(null, ApiException.PHOTO_UPLOADING_ERROR, null)
                    )
                }
            )
        }
        return Result.failure(ApiException(null, ApiException.PHOTO_UPLOADING_ERROR, null))
    }
}