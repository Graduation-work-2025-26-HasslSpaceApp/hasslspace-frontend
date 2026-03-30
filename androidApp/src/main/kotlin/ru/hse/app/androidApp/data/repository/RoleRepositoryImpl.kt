package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.model.UpdateRoleDto
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.model.entity.toDto
import ru.hse.app.androidApp.domain.repository.RoleRepository
import javax.inject.Inject

class RoleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : RoleRepository {

    override suspend fun createServerRole(
        serverId: String,
        createRole: CreateRole
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.createServerRole(serverId, createRole.toDto()) }
    }

    override suspend fun deleteRole(serverId: String, roleId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteRole(serverId, roleId) }
    }

    override suspend fun getServerRoles(serverId: String): Result<List<RoleInfo>> {
        return apiCaller.safeApiCall { apiService.getServerRoles(serverId) }.mapCatching { roles ->
            roles.map { it.toDomain() }
        }
    }

    override suspend fun assignRole(
        serverId: String,
        targetUserId: String,
        roleId: String
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.assignRole(serverId, targetUserId, roleId) }
    }

    override suspend fun revokeRole(
        serverId: String,
        targetUserId: String,
        roleId: String
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.revokeRole(serverId, targetUserId, roleId) }
    }

    override suspend fun patchRole(
        serverId: String,
        roleId: String,
        name: String?,
        position: Int?,
        color: String?
    ): Result<String> {
        val updateRoleDto = UpdateRoleDto(name, color, position)
        return apiCaller.safeApiCall { apiService.patchRole(serverId, roleId, updateRoleDto) }
    }
}
