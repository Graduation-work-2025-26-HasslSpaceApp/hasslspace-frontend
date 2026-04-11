package ru.hse.app.androidApp.domain.usecase.servers

import android.net.Uri
import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.domain.usecase.photo.UploadPhotoUseCase
import ru.hse.app.androidApp.domain.usecase.photo.UploadServerPhotoUseCase
import javax.inject.Inject

class UpdateServerPhotoUseCase @Inject constructor(
    private val uploadPhotoUseCase: UploadServerPhotoUseCase,
    private val patchServerPropertiesUseCase: PatchServerPropertiesUseCase
) {
    suspend operator fun invoke(
        serverId: String,
        imageUri: Uri?,
        photoUrl: String?
    ): Result<String> {
        val uploadResult = uploadPhotoUseCase.invoke(imageUri, photoUrl)
        uploadResult.fold(
            onSuccess = {
                if (photoUrl == null) {
                    return patchServerPropertiesUseCase.invoke(serverId, null, it)
                }
                return uploadResult
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