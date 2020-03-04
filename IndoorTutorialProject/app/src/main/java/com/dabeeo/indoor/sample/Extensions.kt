package com.dabeeo.indoor.sample

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

fun Context.toastMessage(content: String) {
    Toast.makeText(MyApp.INSTANCE, content, Toast.LENGTH_SHORT).show()
}

fun Context.toastError(message: String?) {
    if (TextUtils.isEmpty(message)) {
        toastMessage(getString(R.string.error_message))
    } else {
        toastMessage(message!!)
    }
}