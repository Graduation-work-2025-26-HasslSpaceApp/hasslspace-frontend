package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.ChatInfoDto

data class ChatInfo(
    val id: String,
    val name: String,
    val chatMembers: List<ChatMember>,
) {
    data class ChatMember(
        val id: String,
        val name: String,
        val username: String,
        val status: String,
        val photoURL: String?,
        val isCurrentUser: Boolean,
    )
}

fun ChatInfoDto.toDomain(curUserId: String): ChatInfo {
    return ChatInfo(
        id = this.id,
        name = this.name?:"",
        chatMembers = this.chatMembers.map { it.toDomain(curUserId) }
    )
}

private fun ChatInfoDto.ChatMemberDto.toDomain(curUserId: String): ChatInfo.ChatMember {
    return ChatInfo.ChatMember(
        id = this.id,
        name = this.name,
        username = this.username,
        status = this.status,
        photoURL = this.photoURL,
        isCurrentUser = (this.id == curUserId)
    )
}