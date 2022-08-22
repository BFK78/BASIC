package com.example.basic.core.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.basic.R
import com.razorpay.Checkout
import org.json.JSONObject
import java.lang.Exception


fun startPayment(
    amount: Int,
    activity: Activity
) {

    val checkout = Checkout()

    checkout.setKeyID("rzp_test_qQgdSOtw8Egc2H")
    checkout.setImage(R.drawable.basic_black_logo)

    val obj = JSONObject()

    try {

        obj.put("name", "Basics")
        obj.put("description", "Payment is remaining for your products")
        obj.put("theme.color", "black")
        obj.put("currency", "INR")
        obj.put("amount", amount)
        obj.put("prefill.contact", "6235632987")
        obj.put("prefill.email", "basimbfk781@gmail.com")

        checkout.open(activity, obj)

    } catch (e: Exception) {
        Log.i("razor", e.message.toString())
        e.printStackTrace()
    }

}

