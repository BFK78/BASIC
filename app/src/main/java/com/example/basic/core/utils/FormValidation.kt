package com.example.basic.core.utils

import android.text.TextUtils
import android.util.Patterns

fun String.checkEmail(): Boolean {
    return !TextUtils.isEmpty(this) &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.checkPassword(): Boolean {
    return !TextUtils.isEmpty(this) &&
            this.contains(regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
}

fun String.checkNumber(): Boolean {
    return !TextUtils.isEmpty(this) &&
            Patterns.PHONE.matcher(this).matches() &&
            this.length == 10
}

fun String.checkUsername(): Boolean {
    return !TextUtils.isEmpty(this) &&
            this.length > 5
}