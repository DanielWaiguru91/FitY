package tech.danielwaiguru.fity.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.fity.database.Run
import tech.danielwaiguru.fity.repositories.RunRepository

class RunViewModel @ViewModelInject constructor(
    private val runRepository: RunRepository): ViewModel() {
    val allRuns = runRepository.getAllRuns()
    fun saveRun(run: Run) = viewModelScope.launch { runRepository.saveRun(run) }
    fun totalTime() = runRepository.getTotalTime()
    fun totalDistance() = runRepository.getTotalDistance()
    fun totalCaloriesBurned() = runRepository.getTotalCaloriesBurned()
    fun runsByDistance() = runRepository.getRunsByDistance()
    fun runsByTime() = runRepository.getRunsByTime()
    fun runsBySpeed() = runRepository.getRunsBySpeed()
    fun runsByCalories() = runRepository.getRunsByCalories()
    suspend fun deleteRun(run: Run) = viewModelScope.launch { runRepository.deleteRun(run) }
    suspend fun deleteAllRuns() = viewModelScope.launch { runRepository.deleteAllRuns() }
}