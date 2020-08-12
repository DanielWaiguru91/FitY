package tech.danielwaiguru.fity.repositories

import tech.danielwaiguru.fity.database.Run
import tech.danielwaiguru.fity.database.RunDao
import javax.inject.Inject

class RunRepository @Inject constructor(private val runDao: RunDao) {
    /**
     * Querying the database
     */
    suspend fun saveRun(run: Run) = runDao.saveRun(run)
    fun getAllRuns() = runDao.getAllRuns()
    fun getTotalTime() = runDao.getTotalTime()
    fun getTotalDistance() = runDao.getTotalDistanceCovered()
    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()
    fun getRunsByDistance() = runDao.getRunsByDistance()
    fun getRunsByTime() = runDao.getRunsByTime()
    fun getRunsBySpeed() = runDao.getRunsBySpeed()
    fun getRunsByCalories() = runDao.getRunsByCalories()
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)
    suspend fun deleteAllRuns() = runDao.deleteAllRuns()
}