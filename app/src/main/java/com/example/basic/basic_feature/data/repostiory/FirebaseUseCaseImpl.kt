package com.example.basic.basic_feature.data.repostiory

import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FirebaseUseCaseImpl (): FirebaseUseCase {

    private val fireStore = FirebaseFirestore.getInstance()
    override suspend fun getProductModelDto(collection: String): QuerySnapshot? {
        return fireStore.collection(collection).get().await()
    }
}