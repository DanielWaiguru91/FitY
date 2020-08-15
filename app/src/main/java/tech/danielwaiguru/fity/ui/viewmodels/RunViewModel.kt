package tech.danielwaiguru.fity.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.fity.database.Run
import tech.danielwaiguru.fity.repositories.RunRepository
import tech.danielwaiguru.fity.utils.SortCriteria

class RunViewModel @ViewModelInject constructor(
    private val runRepository: RunRepository): ViewModel() {
    val allRuns = runRepository.getAllRuns()
    //CRUD
    fun saveRun(run: Run) = viewModelScope.launch { runRepository.saveRun(run) }
    suspend fun deleteRun(run: Run) = viewModelScope.launch { runRepository.deleteRun(run) }
    suspend fun deleteAllRuns() = viewModelScope.launch { runRepository.deleteAllRuns() }
    //Math functions
    fun totalTime() = runRepository.getTotalTime()
    fun totalDistance() = runRepository.getTotalDistance()
    fun totalCaloriesBurned() = runRepository.getTotalCaloriesBurned()
    //Sorting
    private fun runsByDate() = runRepository.getRunsByDate()
    private fun runsByDistance() = runRepository.getRunsByDistance()
    private fun runsByTime() = runRepository.getRunsByTime()
    private fun runsBySpeed() = runRepository.getRunsBySpeed()
    private fun runsByCalories() = runRepository.getRunsByCalories()

    val runs = MediatorLiveData<List<Run>>()
    var criteria = SortCriteria.DATE
    fun getSortedRuns(){
        runs.addSource(runsByDate()){result ->
            if (criteria == SortCriteria.DATE){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsByDistance()) { result ->
            if (criteria == SortCriteria.DISTANCE){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsByTime()) { result ->
            if (criteria == SortCriteria.TIME){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsBySpeed()) { result ->
            if (criteria == SortCriteria.SPEED){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsByCalories()) { result ->
            if (criteria == SortCriteria.CALORIES){
                result?.let { runs.value = it }
            }
        }
    }
    fun sortRuns(sortCriteria: SortCriteria) = when (sortCriteria){
        SortCriteria.DATE -> runsByDate().value?.let { runs.value = it }
        SortCriteria.DISTANCE -> runsByDistance().value?.let { runs.value = it }
        SortCriteria.TIME -> runsByTime().value?.let { runs.value = it }
        SortCriteria.SPEED -> runsBySpeed().value?.let { runs.value = it }
        SortCriteria.CALORIES -> runsByCalories().value?.let { runs.value = it }
    }.also { this.criteria = sortCriteria }
}