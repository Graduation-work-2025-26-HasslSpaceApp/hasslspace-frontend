package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoExtendedDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("username")
    val nickname: String,

    @JsonProperty("status")
    val status: String,

    @JsonProperty("photoUrl")
    val photoURL: String?,

    @JsonProperty("description")
    val description: String?,

    @JsonProperty("friendStatus")
    val friendshipStatus: StatusType
) {
    enum class StatusType {
        FRIEND,
        INCOMING_REQUEST,
        OUTGOING_REQUEST,
        BLOCKED,
        NONE
    }
}
