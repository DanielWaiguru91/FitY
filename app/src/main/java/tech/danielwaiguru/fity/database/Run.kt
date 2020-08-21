package tech.danielwaiguru.fity.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Run (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val runDate: Long,
    val averageSpeed: Float,
    val distance: Float,
    val timeTaken: Long,
    val caloriesBurned: Int,
    val image: Bitmap
)