package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUser

class AddUsernameUseCase(
    private val firebaseUser: FirebaseUser
) {

    suspend operator fun invoke(username: String) {
        firebaseUser.addUsername(username = username)
    }

}