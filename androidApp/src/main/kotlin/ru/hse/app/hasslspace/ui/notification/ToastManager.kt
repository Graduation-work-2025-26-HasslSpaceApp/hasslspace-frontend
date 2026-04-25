package ru.hse.app.hasslspace.ui.notification

import android.content.Context
import android.widget.Toast

class ToastManager(private val context: Context) {
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}
