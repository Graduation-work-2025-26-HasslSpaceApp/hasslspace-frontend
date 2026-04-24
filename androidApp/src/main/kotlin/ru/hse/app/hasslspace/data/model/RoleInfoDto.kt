package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RoleInfoDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("color")
    val color: String?,

    @JsonProperty("position")
    val position: Int?,

    @JsonProperty("members")
    val members: List<RoleMemberDto>,
) {
    data class RoleMemberDto(
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