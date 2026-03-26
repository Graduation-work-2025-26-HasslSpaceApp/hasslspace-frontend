//package ru.hse.app.androidApp.domain.usecase.servers
//
//import ru.hse.app.androidApp.domain.model.entity.RoleInfo
//import ru.hse.app.androidApp.domain.repository.ServerRepository
//import javax.inject.Inject
//
//class GetServerUserRolesUseCase @Inject constructor(
//    private val serverRepository: ServerRepository
//) {
//    suspend operator fun invoke(userId: String, serverId: String): Result<List<RoleInfo>> {
//        return serverRepository.getServerUserRoles(userId, serverId)
//    }
//}