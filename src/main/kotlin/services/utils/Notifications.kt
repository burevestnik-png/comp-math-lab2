package services.utils

import tornadofx.View
import views.fragments.Notification
import views.fragments.NotificationType

class Notifications {
    companion object {
        fun info(view: View, content: String? = "") {
            view.openInternalWindow(
                Notification::class,
                params = mapOf(
                    Notification::type to NotificationType.INFO,
                    "content" to content
                )
            )
        }
    }
}