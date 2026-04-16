package com.example.luontopeli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.local.AppDatabase
import com.example.luontopeli.data.local.entity.WalkSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val _allSessions = MutableStateFlow<List<WalkSession>>(emptyList())
    val allSessions: StateFlow<List<WalkSession>> = _allSessions.asStateFlow()

    private val _totalSpots = MutableStateFlow(0)
    val totalSpots: StateFlow<Int> = _totalSpots.asStateFlow()

    init {
        viewModelScope.launch {
            db.walkSessionDao().getAllSessions().collect { sessions ->
                _allSessions.value = sessions
            }
        }

        viewModelScope.launch {
            db.natureSpotDao().getAllSpots().collect { spots ->
                _totalSpots.value = spots.size
            }
        }
    }
}