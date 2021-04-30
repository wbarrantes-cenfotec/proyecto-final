package com.example.pokeapp.extensions

import android.view.View

fun Boolean.mapToVisibility() : Int = if (this) View.VISIBLE else View.GONE