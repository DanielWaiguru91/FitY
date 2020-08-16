package tech.danielwaiguru.fity.ui.viewmodels

import androidx.lifecycle.ViewModel
import tech.danielwaiguru.fity.repositories.RunRepository
import javax.inject.Inject

class RunReportViewModel @Inject constructor(private val runRepository: RunRepository): ViewModel() {
    fun runsByDate() = runRepository.getRunsByDate()
    fun totalTime() = runRepository.getTotalTime()
    fun totalDistance() = runRepository.getTotalDistance()
    fun totalCaloriesBurned() = runRepository.getTotalCaloriesBurned()
    fun totalSpeed() = runRepository.getTotalSpeed()
}