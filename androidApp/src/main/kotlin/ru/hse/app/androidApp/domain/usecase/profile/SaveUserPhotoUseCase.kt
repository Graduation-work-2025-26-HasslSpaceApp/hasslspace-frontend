package ru.hse.app.androidApp.domain.usecase.profile

import android.net.Uri
import jakarta.inject.Inject
import ru.hse.app.androidApp.data.exception.ApiException
import ru.hse.app.androidApp.domain.repository.UserRepository
import ru.hse.app.androidApp.domain.usecase.photo.UploadPhotoUseCase

class SaveUserPhotoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val uploadPhotoUseCase: UploadPhotoUseCase,
) {

    suspend operator fun invoke(
        imageUri: Uri?,
        photoUrl: String?
    ): Result<String> {
        val uploadResult = uploadPhotoUseCase.invoke(imageUri, photoUrl)
        uploadResult.fold(
            onSuccess = {
                return userRepository.saveUserPhoto(it)
            },
            onFailure = {
                return Result.failure(
                    uploadResult.exceptionOrNull()
                    //TODO во всех юз кейсах сделать преобразование ошибки апи в ошибку UI
                        ?: ApiException(null, ApiException.PHOTO_UPLOADING_ERROR, null)
                )
            }
        )
    }
}