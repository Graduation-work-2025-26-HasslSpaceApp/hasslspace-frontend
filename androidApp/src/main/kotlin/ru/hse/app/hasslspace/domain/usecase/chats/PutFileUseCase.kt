package ru.hse.app.hasslspace.domain.usecase.chats

import android.net.Uri
import coil3.ImageLoader
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.domain.repository.ChatRepository
import ru.hse.app.hasslspace.domain.service.common.FileConverterService

class PutFileUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val imageLoader: ImageLoader,
    private val fileConverterService: FileConverterService,
) {
    suspend operator fun invoke(
        file: Uri,
        photoUrl: String?
    ): Result<String> {
//        imageLoader.memoryCache?.clear()todo
//        imageLoader.diskCache?.clear()

        val photoLimit = 100L * 1024 * 1024  // 100 МБ
        val fileLimit = 1L * 1024 * 1024 * 1024  // 1 ГБ

        val userFileMultipart = file.let { fileConverterService.uriToMultipart(it, "file") }

        userFileMultipart?.let { multipart ->
            val isPhoto = fileConverterService.isPhoto(file)
            val fileType = if (isPhoto) "PHOTO" else "FILE"
            val typePart = fileType.toRequestBody("text/plain".toMediaTypeOrNull())

            val size = fileConverterService.getFileSize(file)

            if (size != null && ((isPhoto && size > photoLimit) || (!isPhoto && size > fileLimit))) { // за пределами лимита на загрузку
                return Result.failure(
                    ApiException(
                        null,
                        "Слишком большой файл",
                        null
                    )
                )
            }

            val fileUrlPart = photoUrl
                ?.takeIf { it.isNotBlank() }
                ?.toRequestBody("text/plain".toMediaType())

            val result = chatRepository.uploadFile(multipart, fileUrlPart, typePart)

//            imageLoader.memoryCache?.clear()
//            imageLoader.diskCache?.clear()

            return result
        }

        return Result.failure(
            ApiException(
                null,
                ApiException.FILE_UPLOADING_ERROR + " multipart is null",
                null
            )
        )
    }
}