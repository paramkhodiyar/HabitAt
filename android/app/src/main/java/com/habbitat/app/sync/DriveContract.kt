package com.habbitat.app.sync

import com.google.gson.annotations.SerializedName

data class SyncMetadataEntry(
    @SerializedName("habitId") val habitId: Long,
    @SerializedName("habitName") val habitName: String,
    @SerializedName("habitSlug") val habitSlug: String,
    @SerializedName("date") val date: String, // DD-MM-YYYY format
    @SerializedName("driveFileId") val driveFileId: String = "",
    @SerializedName("verified") val verified: Boolean = true,
    @SerializedName("confidence") val confidence: Float = 1.0f,
    @SerializedName("completedAt") val completedAt: Long = System.currentTimeMillis(),
    @SerializedName("streak") val streak: Int = 1
)

data class SyncMetadataFile(
    @SerializedName("version") val version: Int = 1,
    @SerializedName("lastSyncedAt") val lastSyncedAt: Long = System.currentTimeMillis(),
    @SerializedName("entries") val entries: List<SyncMetadataEntry> = emptyList()
)

object DriveContract {
    const val ROOT_FOLDER_NAME = "HabitAt"
    const val METADATA_FILE_NAME = "sync-metadata.json"

    fun toSlug(text: String): String {
        return text.lowercase()
            .replace(Regex("[^a-z0-9]"), "-")
            .replace(Regex("-+"), "-")
            .trim('-')
            .ifBlank { "habit" }
    }
}
