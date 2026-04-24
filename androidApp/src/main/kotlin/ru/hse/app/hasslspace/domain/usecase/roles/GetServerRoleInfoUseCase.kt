package ru.hse.app.hasslspace.domain.usecase.roles

import ru.hse.app.hasslspace.domain.model.entity.RoleInfo
import javax.inject.Inject

class GetServerRoleInfoUseCase @Inject constructor() {
    operator fun invoke(roleId: String, roles: List<RoleInfo>): RoleInfo? {
        return roles.firstOrNull { it.id == roleId }
    }
}