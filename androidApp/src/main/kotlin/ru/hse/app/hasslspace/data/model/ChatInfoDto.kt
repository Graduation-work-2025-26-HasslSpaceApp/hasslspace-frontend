package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatInfoDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String?,

    @JsonProperty("chatMembers")
    val chatMembers: List<ChatMemberDto>,
) {
    data class ChatMemberDto(
        @JsonProperty("id")
        val id: String,

        @JsonProperty("name")
        val name: String,

        @JsonProperty("username")
        val username: String,

        @JsonProperty("status")
        val status: String,

        @JsonProperty("photoUrl")
        val photoURL: String?,
    )
}