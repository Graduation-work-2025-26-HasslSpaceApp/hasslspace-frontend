package ru.hse.app.hasslspace.domain.usecase.chats

import android.net.Uri
import ru.hse.app.hasslspace.data.exception.ApiException
import ru.hse.app.hasslspace.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val putFileUseCase: PutFileUseCase,
) {
    suspend operator fun invoke(
        chatId: String, content: String, attachments: List<Uri>
    ): Result<String> {
        if (attachments.isEmpty()) {
            return chatRepository.sendMessage(chatId, content, null)
        }

        val file = attachments.first()
        val uploadResult = putFileUseCase.invoke(file, null) // todo
        uploadResult.fold(onSuccess = { fileUrl ->
            return chatRepository.sendMessage(chatId, content, fileUrl)
        }, onFailure = {
            return Result.failure(
                uploadResult.exceptionOrNull()
                //TODO во всех юз кейсах сделать преобразование ошибки апи в ошибку UI
                    ?: ApiException(null, ApiException.FILE_UPLOADING_ERROR, null)
            )
        })
    }
}