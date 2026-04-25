package ru.hse.app.hasslspace.domain.usecase.servers

import ru.hse.app.hasslspace.ui.entity.model.ServerShortUiModel
import javax.inject.Inject

class SearchServersUseCase @Inject constructor() {

    operator fun invoke(
        list: List<ServerShortUiModel>,
        value: String
    ): List<ServerShortUiModel> {
        val filteredList = search(list, value)
        return filteredList
    }

    private fun search(
        list: List<ServerShortUiModel>,
        value: String
    ): List<ServerShortUiModel> {
        return list.filter { it.name.contains(value, ignoreCase = true) }
    }
}