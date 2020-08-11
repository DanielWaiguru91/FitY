package tech.danielwaiguru.fity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RunDao {
    @Insert
    suspend fun saveRun(run: Run)
    @Query("SELECT * FROM runs_table")
    fun getAllRuns(): LiveData<List<Run>>
    @Delete
    suspend fun deleteRun(run: Run)
    @Query("DELETE FROM runs_table")
    suspend fun deleteAllRuns()
}