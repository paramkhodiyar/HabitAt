package com.habitAt.app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.habitAt.app.ui.theme.habitAtTypography
import com.habitAt.app.ui.theme.InkPrimary

@Composable
fun MotivationalBanner(
    userName: String = "Param",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "Welcome $userName,",
            style = habitAtTypography.headlineMedium.copy(
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp
            ),
            color = InkPrimary
        )
    }
}
