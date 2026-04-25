package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.ui.entity.model.ServerMemberUiModel
import javax.inject.Inject

class SearchMembersUseCase @Inject constructor() {

    operator fun invoke(
        list: List<ServerMemberUiModel>,
        value: String
    ): List<ServerMemberUiModel> {
        val filteredList = search(list, value)
        return filteredList
    }

    private fun search(
        list: List<ServerMemberUiModel>,
        value: String
    ): List<ServerMemberUiModel> {
        return list.filter { it.name.contains(value, ignoreCase = true) }
    }
}