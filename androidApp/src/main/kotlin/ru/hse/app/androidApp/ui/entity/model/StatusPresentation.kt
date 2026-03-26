package ru.hse.app.androidApp.ui.entity.model

import ru.hse.app.androidApp.R

enum class StatusPresentation(val painter: Int, val label: String) {
    ONLINE(R.drawable.active, label = "В сети"),
    INVISIBLE(R.drawable.invisible, label = "Невидимка"),
    DO_NOT_DISTURB(R.drawable.donotdisturb, label = "Не беспокоить"),
    OFFLINE(R.drawable.notactive, label = "Не активен")
}

fun String.toStatusPresentation(): StatusPresentation {
    return when (this) {
        "В сети" -> StatusPresentation.ONLINE
        "Невидимка" -> StatusPresentation.INVISIBLE
        "Не беспокоить" -> StatusPresentation.DO_NOT_DISTURB
        "Не активен" -> StatusPresentation.OFFLINE
        else -> StatusPresentation.INVISIBLE
    }
}

fun StatusPresentation.toDomain(): String {
    return when (this) {
        StatusPresentation.ONLINE -> "В сети"
        StatusPresentation.INVISIBLE -> "Невидимка"
        StatusPresentation.DO_NOT_DISTURB -> "Не беспокоить"
        StatusPresentation.OFFLINE -> "Не активен"
    }
}