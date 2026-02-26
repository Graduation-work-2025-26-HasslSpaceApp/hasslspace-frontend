package ru.hse.app.androidApp.domain.usecase.friends

import ru.hse.app.androidApp.domain.repository.FriendRepository
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import javax.inject.Inject

class SearchFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    operator fun invoke(
        list: List<FriendUiModel>,
        value: String
    ): List<FriendUiModel> {
        val filteredList = search(list, value)
        return filteredList
    }

    private fun search(
        list: List<FriendUiModel>,
        value: String
    ): List<FriendUiModel> {
        return list.filter { it.name.contains(value, ignoreCase = true) }
    }
}