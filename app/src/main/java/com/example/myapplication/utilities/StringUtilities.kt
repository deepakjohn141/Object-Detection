package com.example.myapplication.utilities

import android.content.Context
import android.widget.Toast

object StringUtilities {

    fun String.showToast(context: Context) {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}