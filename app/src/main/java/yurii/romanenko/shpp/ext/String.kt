package yurii.romanenko.shpp.ext

import java.util.Locale

fun String.firstCharToUpper()=replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
