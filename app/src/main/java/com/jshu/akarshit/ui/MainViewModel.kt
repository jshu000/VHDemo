package com.jshu.akarshit.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jshu.akarshit.repository.AppListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG= "Jashwant MainViewmodel"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppListRepository
) : ViewModel() {

    val apps = repository.getApps()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        Log.d(TAG, "init refresh")
        refresh()
    }

    fun refresh() {
        Log.d(TAG, "refresh refresh")

        viewModelScope.launch {
            repository.refreshApps(278)
        }
    }
}