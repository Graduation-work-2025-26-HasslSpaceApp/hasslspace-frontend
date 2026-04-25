package ru.hse.app.hasslspace.domain.usecase.friends

import ru.hse.app.hasslspace.domain.repository.FriendRepository
import ru.hse.app.hasslspace.ui.entity.model.FriendUiModel
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