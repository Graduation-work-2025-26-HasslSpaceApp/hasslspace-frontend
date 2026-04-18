package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenRequest(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("roomName")
    val roomName: String,

    @JsonProperty("roomType")
    val roomType: RoomType? = null,
) {
    enum class RoomType {
        SERVER,
        PRIVATE_ROOM
    }
}

fun String.toRoomTypeDto(): TokenRequest.RoomType {
    return when (this) {
        "SERVER" -> TokenRequest.RoomType.SERVER
        "PRIVATE_ROOM" -> TokenRequest.RoomType.PRIVATE_ROOM
        else -> TokenRequest.RoomType.PRIVATE_ROOM

    }
}