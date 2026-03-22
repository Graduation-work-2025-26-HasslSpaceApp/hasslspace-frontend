package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.ui.entity.model.ChatShortUiModel
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
        return list.filter { it.title.contains(value, ignoreCase = true) }
    }
}