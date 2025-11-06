package com.downloader.roznamcha.core.extensions

fun String.toDoubleOrZero() = this.toDoubleOrNull() ?: 0.0
fun String.multiply(value: String) = this.toDoubleOrZero() * value.toDoubleOrZero()