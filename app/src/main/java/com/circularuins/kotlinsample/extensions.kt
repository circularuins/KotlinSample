package com.circularuins.kotlinsample

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.widget.Toast

/**
 * Created by circularuins on 2018/01/07.
 */
fun <T : View> View.bindVew(@IdRes id: Int): Lazy<T> = lazy {
    findViewById<T>(id)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}