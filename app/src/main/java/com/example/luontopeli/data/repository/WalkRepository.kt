package com.example.luontopeli.data.repository

import com.example.luontopeli.data.local.dao.WalkSessionDao
import com.example.luontopeli.data.local.entity.WalkSession
import kotlinx.coroutines.flow.Flow

class WalkRepository(private val dao: WalkSessionDao) {

    val allSessions: Flow<List<WalkSession>> = dao.getAllSessions()

    suspend fun insertSession(session: WalkSession) {
        dao.insert(session)
    }

    suspend fun updateSession(session: WalkSession) {
        dao.update(session)
    }

    suspend fun getActiveSession(): WalkSession? {
        return dao.getActiveSession()
    }
}