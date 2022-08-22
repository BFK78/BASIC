package com.example.basic.basic_feature.data.remote.firebase.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseUseCase {

    suspend fun getProductModelDto(collection: String): QuerySnapshot?
}