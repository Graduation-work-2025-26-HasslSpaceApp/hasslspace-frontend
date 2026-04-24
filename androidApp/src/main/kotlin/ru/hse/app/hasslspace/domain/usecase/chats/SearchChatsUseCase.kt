package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.ui.entity.model.ChatShortUiModel
import javax.inject.Inject

class SearchChatsUseCase @Inject constructor() {

    operator fun invoke(
        list: List<ChatShortUiModel>,
        value: String
    ): List<ChatShortUiModel> {
        val filteredList = search(list, value)
        return filteredList
    }

    private fun search(
        list: List<ChatShortUiModel>,
        value: String
    ): List<ChatShortUiModel> {
        return list.filter { it.name.contains(value, ignoreCase = true) }
    }
}