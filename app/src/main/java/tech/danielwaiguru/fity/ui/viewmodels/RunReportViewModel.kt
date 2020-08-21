package tech.danielwaiguru.fity.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import tech.danielwaiguru.fity.repositories.RunRepository

class RunReportViewModel @ViewModelInject constructor(private val runRepository: RunRepository): ViewModel() {
    fun runsByDate() = runRepository.getRunsByDate()
    fun totalTime() = runRepository.getTotalTime()
    fun totalDistance() = runRepository.getTotalDistance()
    fun totalCaloriesBurned() = runRepository.getTotalCaloriesBurned()
    fun totalSpeed() = runRepository.getTotalSpeed()
}