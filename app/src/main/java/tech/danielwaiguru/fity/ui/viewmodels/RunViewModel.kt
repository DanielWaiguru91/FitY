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
    //Sorting
    private val runsByDate = runRepository.getRunsByDate()
    private val runsByDistance = runRepository.getRunsByDistance()
    private val runsByTime = runRepository.getRunsByTime()
    private val runsBySpeed = runRepository.getRunsBySpeed()
    private val runsByCalories = runRepository.getRunsByCalories()

    var runs = MediatorLiveData<List<Run>>()
    var criteria : SortCriteria = SortCriteria.DISTANCE
    init {
        runs.addSource(runsByDate){result ->
            if (criteria == SortCriteria.DATE){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsByDistance) { result ->
            if (criteria == SortCriteria.DISTANCE){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsByTime) { result ->
            if (criteria == SortCriteria.TIME){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsBySpeed) { result ->
            if (criteria == SortCriteria.SPEED){
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsByCalories) { result ->
            if (criteria == SortCriteria.CALORIES){
                result?.let { runs.value = it }
            }
        }
    }
    fun sortRuns(sortCriteria: SortCriteria) = when (sortCriteria){
        SortCriteria.DATE -> runsByDate.value?.let { runs.value = it }
        SortCriteria.DISTANCE -> runsByDistance.value?.let { runs.value = it }
        SortCriteria.TIME -> runsByTime.value?.let { runs.value = it }
        SortCriteria.SPEED -> runsBySpeed.value?.let { runs.value = it }
        SortCriteria.CALORIES -> runsByCalories.value?.let { runs.value = it }
    }.also { this.criteria = sortCriteria }
}