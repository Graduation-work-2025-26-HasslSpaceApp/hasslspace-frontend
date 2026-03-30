package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.model.entity.RoleInfo

interface RoleRepository {
    suspend fun createServerRole(serverId: String, createRole: CreateRole): Result<String>
    suspend fun deleteRole(serverId: String, roleId: String): Result<String>
    suspend fun getServerRoles(serverId: String): Result<List<RoleInfo>>
    suspend fun patchRole(
        serverId: String,
        roleId: String,
        name: String?,
        position: Int?,
        color: String?
    ): Result<String>

    suspend fun assignRole(serverId: String, targetUserId: String, roleId: String): Result<String>
    suspend fun revokeRole(serverId: String, targetUserId: String, roleId: String): Result<String>
}