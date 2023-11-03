package com.example.myapplication.utilities

import kotlinx.coroutines.Dispatchers

object Schedulers {
     val IO = Dispatchers.IO
     val MAIN = Dispatchers.Main
     val COMPUTATION = Dispatchers.Default
}