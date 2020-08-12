package tech.danielwaiguru.fity.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRun(run: Run)

    @Query("SELECT * FROM runs_table")
    fun getAllRuns(): LiveData<List<Run>>

    @Query("SELECT SUM(timeTaken) FROM runs_table")
    fun getTotalTime(): LiveData<Long>

    @Query("SELECT SUM(distance) FROM runs_table")
    fun getTotalDistanceCovered(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM runs_table")
    fun getTotalCaloriesBurned(): LiveData<Long>

    @Query("SELECT * FROM runs_table ORDER BY distance DESC")
    fun getRunsByDistance(): LiveData<List<Run>>

    @Query("SELECT * FROM runs_table ORDER BY timeTaken DESC")
    fun getRunsByTimes(): LiveData<List<Run>>

    @Query("SELECT * FROM runs_table ORDER BY averageSpeed DESC")
    fun getRunsBySpeed(): LiveData<List<Run>>

    @Query("SELECT * FROM runs_table ORDER BY caloriesBurned DESC")
    fun getRunsByCalories(): LiveData<List<Run>>

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("DELETE FROM runs_table")
    suspend fun deleteAllRuns()
}