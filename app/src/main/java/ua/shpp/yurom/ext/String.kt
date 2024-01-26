package ua.shpp.yurom.ext

fun String.firstCharToUpper() =
    this.replaceFirstChar { it.uppercase() }


