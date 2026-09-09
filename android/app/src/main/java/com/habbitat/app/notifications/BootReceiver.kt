package com.habbitat.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.habbitat.app.HabbitAtApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val pendingResult = goAsync()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = (context.applicationContext as HabbitAtApp).repository
                    val habits = repository.getHabitsList()
                    habits.forEach { habit ->
                        ReminderScheduler.scheduleFirstReminder(context, habit)
                    }
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
