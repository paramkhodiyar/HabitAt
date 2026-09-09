package com.habbitat.app.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronPrimary

@Composable
fun ExitConfirmationDialog(
    onConfirmExit: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Exit HabitAt?",
                style = HabbitAtTypography.headlineMedium,
                color = InkPrimary
            )
        },
        text = {
            Text(
                text = "Are you sure you want to exit the application?",
                style = HabbitAtTypography.bodyMedium,
                color = InkSecondary
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmExit,
                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Exit App", color = InkPrimary)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Cancel", color = InkSecondary)
            }
        },
        containerColor = CardSurface,
        shape = RoundedCornerShape(20.dp)
    )
}
