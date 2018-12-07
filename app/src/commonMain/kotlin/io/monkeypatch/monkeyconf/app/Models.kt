package io.monkeypatch.monkeyconf.app

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val firstName: String,
    val lastName: String,
    val bio: String,
    val avatar: String
)

@Serializable
data class Room(
    val name: String
)

@Serializable
data class TechInfo(
    val themes: List<String>,
    val difficulty: String
)

@Serializable
data class Talk(
    val id: String,
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val speakers: List<Speaker>,
    val room: Room,
    @Optional val techInfo: TechInfo? = null,
    val description: String

) {
    fun details(): String = description
}

val Talk.roomDetail: String
    get() = "${room.name} ${techInfo?.themes?.joinToString()?.let { " - $it"} ?: ""}"

val Talk.timeSlot: String
    get() = "${startTime.take(5)} - ${endTime.take(5)}"
