package com.example.basic.basic_feature.domain.use_case

import android.net.Uri
import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUser

class UpdateProfilePicture(
    private val firebaseUser: FirebaseUser
) {

    suspend operator fun invoke(image: Uri) {
        firebaseUser.addPhoto(image = image)
    }

}