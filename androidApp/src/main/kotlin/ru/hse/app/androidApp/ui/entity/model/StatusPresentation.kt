package ru.hse.app.androidApp.ui.entity.model

import ru.hse.app.androidApp.R

enum class StatusPresentation(val painter: Int, val label: String) {
    ACTIVE(R.drawable.active, label = "В сети"),
    INVISIBLE(R.drawable.invisible, label = "Невидимка"),
    DO_NOT_DISTURB(R.drawable.donotdisturb, label = "Не беспокоить"),
    NOT_ACTIVE(R.drawable.notactive, label = "Не активен")
}

fun String.toStatusPresentation(): StatusPresentation {
    return when (this) {
        "В сети" -> StatusPresentation.ACTIVE
        "Невидимка" -> StatusPresentation.INVISIBLE
        "Не беспокоить" -> StatusPresentation.DO_NOT_DISTURB
        "Не активен" -> StatusPresentation.NOT_ACTIVE
        else -> StatusPresentation.INVISIBLE
    }
}

fun StatusPresentation.toDomain(): String {
    return when (this) {
        StatusPresentation.ACTIVE -> "В сети"
        StatusPresentation.INVISIBLE -> "Невидимка"
        StatusPresentation.DO_NOT_DISTURB -> "Не беспокоить"
        StatusPresentation.NOT_ACTIVE -> "Не активен"
    }
}