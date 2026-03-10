package ru.hse.app.androidApp.domain.usecase.channel

import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class PatchChannelPropertiesUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    //TODO тут несколько эндпоинтов
    suspend operator fun invoke(): Result<String> {
        return Result.success("")
    }
}