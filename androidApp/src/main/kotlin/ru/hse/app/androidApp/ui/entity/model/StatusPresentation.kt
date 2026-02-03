package ru.hse.app.androidApp.ui.entity.model

import ru.hse.app.androidApp.R

enum class StatusPresentation(val painter: Int, val label: String) {
    ACTIVE(R.drawable.active, label = "В сети"),
    INVISIBLE(R.drawable.invisible, label = "Невидимка"),
    DO_NOT_DISTURB(R.drawable.donotdisturb, label = "Не беспокоить"),
    NOT_ACTIVE(R.drawable.notactive, label = "Не активен")
}