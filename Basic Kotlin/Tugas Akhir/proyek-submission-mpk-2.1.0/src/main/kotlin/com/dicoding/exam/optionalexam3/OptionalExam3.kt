package com.dicoding.exam.optionalexam3

// TODO
fun manipulateString(str: String, int: Int): String {
    val textPart = str.takeWhile { !it.isDigit() }
    val numberPart = str.dropWhile { !it.isDigit() }

    return if (numberPart.isNotEmpty()) {
        val number = numberPart.toInt()
        "$textPart${number * int}"
    } else {
        "$str$int"
    }
}
