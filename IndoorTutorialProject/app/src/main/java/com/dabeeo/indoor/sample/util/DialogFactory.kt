package com.dabeeo.indoor.sample.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface


object DialogFactory {

    fun createDialog(
        context: Context,
        title: String,
        message: String,
        positiveBtnText: String,
        positiveClickListener: DialogInterface.OnClickListener,
        negativeBtnText: String,
        negativeClickListener: DialogInterface.OnClickListener
    ): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveBtnText, positiveClickListener)
            .setNegativeButton(negativeBtnText, negativeClickListener)
            .create()
    }

    fun createLocationDialog(
        context: Context,
        message: String,
        positiveClickListener: DialogInterface.OnClickListener,
        negativeClickListener: DialogInterface.OnClickListener
    ): Dialog {
        return AlertDialog.Builder(context)
            .setTitle("출/도착지를 선택해주세요.")
            .setMessage(message)
            .setNegativeButton("출발지", positiveClickListener)
            .setPositiveButton("도착지", negativeClickListener)
            .create()
    }
}