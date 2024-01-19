package yurii.romanenko.shpp.ext

import java.util.Locale

fun String.firstCharToUpper() =
    this.replaceFirstChar { it.uppercase() }

    //    ^ recommendation from Eugene Orlov

    //  this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
