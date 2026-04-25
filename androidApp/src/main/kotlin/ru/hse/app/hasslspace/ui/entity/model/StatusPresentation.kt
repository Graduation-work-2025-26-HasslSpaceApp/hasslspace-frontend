package ru.hse.app.hasslspace.ui.entity.model

import ru.hse.app.hasslspace.R

enum class StatusPresentation(val painter: Int, val label: String) {
    ONLINE(R.drawable.active, label = "В сети"),
    INVISIBLE(R.drawable.invisible, label = "Невидимка"),
    DO_NOT_DISTURB(R.drawable.donotdisturb, label = "Не беспокоить"),
    OFFLINE(R.drawable.notactive, label = "Не активен")
}

fun String.toStatusPresentation(): StatusPresentation {
    return when (this) {
        "ONLINE" -> StatusPresentation.ONLINE
        "INVISIBLE" -> StatusPresentation.INVISIBLE
        "DO_NOT_DISTURB" -> StatusPresentation.DO_NOT_DISTURB
        "OFFLINE" -> StatusPresentation.OFFLINE
        else -> StatusPresentation.INVISIBLE
    }
}

fun StatusPresentation.toDomain(): String {
    return when (this) {
        StatusPresentation.ONLINE -> "ONLINE"
        StatusPresentation.INVISIBLE -> "INVISIBLE"
        StatusPresentation.DO_NOT_DISTURB -> "DO_NOT_DISTURB"
        StatusPresentation.OFFLINE -> "OFFLINE"
    }
}