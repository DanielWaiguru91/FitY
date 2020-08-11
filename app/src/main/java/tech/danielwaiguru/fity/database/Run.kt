package tech.danielwaiguru.fity.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Run (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val runDate: Long,
    val averageSpeed: Float,
    val distance: Float,
    val timeTaken: Float,
    val caloriesBurned: Int,
    val image: Int
)