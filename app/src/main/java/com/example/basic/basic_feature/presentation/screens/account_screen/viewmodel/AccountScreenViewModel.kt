package com.example.basic.basic_feature.presentation.screens.account_screen.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic.basic_feature.domain.use_case.UpdateProfilePicture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val updateProfilePicture: UpdateProfilePicture
): ViewModel() {

    fun updateProfilePic(image: Uri) = viewModelScope.launch {
        updateProfilePicture(image = image)
    }

}