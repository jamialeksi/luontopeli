package com.example.luontopeli.data.remote

import com.example.luontopeli.data.local.entity.NatureSpot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirestoreManager {

    private val db = FirebaseFirestore.getInstance()
    private val spotsCollection = db.collection("nature_spots")

    suspend fun saveSpot(spot: NatureSpot): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            val data = mapOf(
                "id" to spot.id,
                "name" to spot.name,
                "latitude" to spot.latitude,
                "longitude" to spot.longitude,
                "imageLocalPath" to spot.imageLocalPath,
                "imageFirebaseUrl" to spot.imageFirebaseUrl,
                "plantLabel" to spot.plantLabel,
                "confidence" to spot.confidence,
                "userId" to spot.userId,
                "timestamp" to spot.timestamp,
                "synced" to true
            )

            spotsCollection.document(spot.id)
                .set(data)
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
    }

    fun getUserSpots(userId: String): Flow<List<NatureSpot>> = callbackFlow {
        val listener = spotsCollection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val spots = snapshot?.documents?.mapNotNull { doc ->
                    NatureSpot(
                        id = doc.getString("id") ?: "",
                        name = doc.getString("name") ?: "",
                        latitude = doc.getDouble("latitude") ?: 0.0,
                        longitude = doc.getDouble("longitude") ?: 0.0,
                        imageFirebaseUrl = doc.getString("imageFirebaseUrl"),
                        plantLabel = doc.getString("plantLabel"),
                        confidence = doc.getDouble("confidence")?.toFloat(),
                        userId = doc.getString("userId"),
                        timestamp = doc.getLong("timestamp") ?: 0L,
                        synced = true
                    )
                } ?: emptyList()

                trySend(spots)
            }

        awaitClose { listener.remove() }
    }
}