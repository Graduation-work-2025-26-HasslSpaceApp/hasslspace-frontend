package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoDto(
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

    @JsonProperty("type")
    val type: TypeDto,
)