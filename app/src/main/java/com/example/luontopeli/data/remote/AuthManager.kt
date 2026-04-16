package com.example.luontopeli.data.remote

import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class AuthManager {

    private val auth = FirebaseAuth.getInstance()

    val currentUserId: String
        get() = auth.currentUser?.uid ?: "anonymous"

    val isSignedIn: Boolean
        get() = auth.currentUser != null

    suspend fun signInAnonymously(): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            auth.signInAnonymously()
                .addOnSuccessListener { result ->
                    continuation.resume(Result.success(result.user?.uid ?: "unknown"))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}