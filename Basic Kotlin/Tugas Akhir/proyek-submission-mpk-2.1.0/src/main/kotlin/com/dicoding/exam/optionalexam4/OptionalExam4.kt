package com.dicoding.exam.optionalexam4

// TODO
fun getMiddleCharacters(string: String): String {
    val length = string.length
    val middle = length / 2

    return if (length % 2 == 0) {
        string.substring(middle - 1, middle + 1)
    } else {
        string.substring(middle, middle + 1)
    }
}
