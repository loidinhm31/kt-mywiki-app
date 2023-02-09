package com.july.wikipedia.utils

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(msgId: Int, length: Int) {
    showSnackbar(context.getString(msgId), length)
}

fun View.showSnackbar(msg: String, length: Int) {
    showSnackbar(msg, length, null, {})
}

fun View.showSnackbar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackbar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    }
}

fun ImageView.showButtonAnimation(imageView: ImageView) {
    val anim = AlphaAnimation(1.0f, 0.2f)
    anim.duration = 1000
    imageView.startAnimation(anim)
}

fun showToast(context: Context, message: String, duration: Int) {
    Toast.makeText(context, message, duration).show()
}