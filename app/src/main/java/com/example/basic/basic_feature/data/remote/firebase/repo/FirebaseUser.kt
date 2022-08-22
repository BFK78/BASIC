package com.example.basic.basic_feature.data.remote.firebase.repo

import android.net.Uri
import com.example.basic.core.utils.LoadingState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseUser {

    suspend fun userSignIn(email: String, password: String): AuthResult?

    suspend fun userRegister(email: String, password: String): AuthResult?

    suspend fun addPhoto(image: Uri)

    suspend fun addUsername(username: String)
}