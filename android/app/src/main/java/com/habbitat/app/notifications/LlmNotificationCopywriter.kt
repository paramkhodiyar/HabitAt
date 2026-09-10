package com.habitAt.app.notifications

import com.habitAt.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class LlmNotificationCopywriter : NotificationCopywriter {

    private val client = OkHttpClient.Builder()
        .connectTimeout(4, TimeUnit.SECONDS)
        .readTimeout(4, TimeUnit.SECONDS)
        .build()

    override suspend fun generateCopy(
        habitName: String,
        frequency: String,
        targetDurationMinutes: Int,
        streakCount: Int,
        escalationLevel: Int,
        tone: String
    ): NotificationCopy = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.OPENROUTER_API_KEY
            .ifBlank { BuildConfig.AI_NOTIFICATION_API_KEY }
            .ifBlank { BuildConfig.AI_VERIFICATION_API_KEY }
            .trim()

        val fallbackCopy = NotificationCopy(
            title = NotificationTemplates.getTitle(habitName, frequency, escalationLevel),
            message = NotificationTemplates.getMessage(
                habitName = habitName,
                frequency = frequency,
                targetDurationMinutes = targetDurationMinutes,
                streakCount = streakCount,
                escalationLevel = escalationLevel
            )
        )

        if (apiKey.isBlank()) {
            return@withContext fallbackCopy
        }

        // Strict 5-second timeout
        val result = withTimeoutOrNull(5000L) {
            try {
                val prompt = """
                    You are an unyielding, brutal AI discipline supervisor for a habit tracking app called habitAt.
                    Habit Name: "$habitName"
                    Frequency: "${frequency.uppercase()}"
                    Target Duration: $targetDurationMinutes minutes
                    Current Streak: $streakCount days
                    Escalation Level: $escalationLevel (1 = initial alert, 2 = stern warning, 3 = urgent streak alert, 4 = final midnight warning)
                    Tone: Brutal, rude, uncompromising, sarcastic discipline supervisor.

                    Context & Content Rules:
                    - Explicitly mention that the ${frequency.uppercase()} habit "$habitName" is PENDING.
                    - Remind the user to log photo proof before EOD (End Of Day).
                    - Title MUST be strictly under 40 characters.
                    - Message MUST be strictly under 120 characters.
                    - Absolutely NO emojis anywhere.
                    
                    Return strictly valid JSON format with keys "title" and "message".
                """.trimIndent()

                val (rawTitle, rawMessage) = if (apiKey.startsWith("sk-or-")) {
                    val jsonPayload = JSONObject().apply {
                        put("model", "google/gemini-2.5-flash")
                        put("messages", JSONArray().apply {
                            put(JSONObject().apply {
                                put("role", "user")
                                put("content", prompt)
                            })
                        })
                    }

                    val request = Request.Builder()
                        .url("https://openrouter.ai/api/v1/chat/completions")
                        .addHeader("Authorization", "Bearer $apiKey")
                        .addHeader("Content-Type", "application/json")
                        .post(jsonPayload.toString().toRequestBody("application/json".toMediaType()))
                        .build()

                    val response = client.newCall(request).execute()
                    val bodyString = response.body?.string() ?: return@withTimeoutOrNull null
                    if (!response.isSuccessful) return@withTimeoutOrNull null

                    val choices = JSONObject(bodyString).getJSONArray("choices")
                    if (choices.length() == 0) return@withTimeoutOrNull null
                    val text = choices.getJSONObject(0).getJSONObject("message").getString("content")
                    val parsed = JSONObject(text.replace("```json", "").replace("```", "").trim())
                    Pair(parsed.optString("title", fallbackCopy.title), parsed.optString("message", fallbackCopy.message))
                } else {
                    val jsonPayload = JSONObject().apply {
                        put("contents", JSONArray().put(JSONObject().apply {
                            put("parts", JSONArray().put(JSONObject().apply {
                                put("text", prompt)
                            }))
                        }))
                        put("generationConfig", JSONObject().apply {
                            put("responseMimeType", "application/json")
                        })
                    }

                    val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
                    val request = Request.Builder()
                        .url(endpoint)
                        .post(jsonPayload.toString().toRequestBody("application/json".toMediaType()))
                        .build()

                    val response = client.newCall(request).execute()
                    val bodyString = response.body?.string() ?: return@withTimeoutOrNull null
                    if (!response.isSuccessful) return@withTimeoutOrNull null

                    val jsonResp = JSONObject(bodyString)
                    val candidates = jsonResp.optJSONArray("candidates") ?: return@withTimeoutOrNull null
                    if (candidates.length() == 0) return@withTimeoutOrNull null
                    val text = candidates.getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")

                    val parsed = JSONObject(text)
                    Pair(parsed.optString("title", fallbackCopy.title), parsed.optString("message", fallbackCopy.message))
                }

                val cleanTitle = NotificationTemplates.enforceLength(rawTitle, maxChars = 40)
                val cleanMessage = NotificationTemplates.enforceLength(rawMessage, maxChars = 120)

                NotificationCopy(
                    title = cleanTitle.ifBlank { fallbackCopy.title },
                    message = cleanMessage.ifBlank { fallbackCopy.message }
                )
            } catch (e: Exception) {
                null
            }
        }

        result ?: fallbackCopy
    }
}
