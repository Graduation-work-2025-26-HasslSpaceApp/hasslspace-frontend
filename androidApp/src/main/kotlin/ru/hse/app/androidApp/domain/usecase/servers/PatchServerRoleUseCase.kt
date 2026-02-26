package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class PatchServerRoleUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    //TODO
    suspend operator fun invoke(): Result<String> {
        return Result.success("")
    }
}