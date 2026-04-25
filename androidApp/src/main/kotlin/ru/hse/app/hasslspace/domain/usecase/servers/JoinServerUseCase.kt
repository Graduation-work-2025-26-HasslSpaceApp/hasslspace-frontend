package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.domain.repository.ServerRepository
import javax.inject.Inject

class JoinServerUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(code: String): Result<String> {
        return serverRepository.joinServer(code)
    }
}