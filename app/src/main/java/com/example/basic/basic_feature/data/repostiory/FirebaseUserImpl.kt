package com.example.basic.basic_feature.data.repostiory

import android.net.Uri
import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUser
import com.example.basic.core.utils.LoadingState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FirebaseUserImpl(
    val context: CoroutineContext
): FirebaseUser {

    private val auth = Firebase.auth

    override suspend fun userSignIn(email: String, password: String): AuthResult = withContext(context) {
        auth.signInWithEmailAndPassword(email, password).await()
    }
    override suspend fun userRegister(email: String, password: String): AuthResult = withContext(context) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun addPhoto(image: Uri) {
        val userProfileChangeRequest = UserProfileChangeRequest.Builder().setPhotoUri(image).build()
        auth.currentUser?.updateProfile(userProfileChangeRequest)
    }

    override suspend fun addUsername(username: String) {
        val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(username).build()
        auth.currentUser?.updateProfile(userProfileChangeRequest)
    }
}