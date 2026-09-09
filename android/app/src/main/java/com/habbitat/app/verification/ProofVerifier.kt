package com.habbitat.app.verification

import android.content.Context
import android.net.Uri
import com.habbitat.app.data.local.entity.Habit

data class VerificationResult(
    val verified: Boolean,
    val confidence: Float, // 0.0 to 1.0
    val reason: String
)

interface ProofVerifier {
    suspend fun verify(habit: Habit, imageUri: Uri, context: Context): VerificationResult
}
