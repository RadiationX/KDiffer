package ru.radiationx.kdiffersample

import android.annotation.SuppressLint
import android.text.Html
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.util.*


@SuppressLint("ConstantLocale")
private val dateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

@Suppress("DEPRECATION")
fun String.formatHtml(): CharSequence {
    return Html.fromHtml(this)
}

fun Date.formatDate(): String {
    return humanReadableReal()
}

fun Int.formatCounter(): String {
    return humanReadableReal()
}

private fun Date.humanReadableFake(): String {
    return time.toString()
}

private fun Int.humanReadableFake(): String {
    return this.toString()
}

private fun Date.humanReadableReal(): String {
    return dateFormat.format(this)
}

private fun Int.humanReadableReal(): String {
    var bytes = this
    if (-1000 < bytes && bytes < 1000) {
        return "$bytes"
    }
    val characterIterator = StringCharacterIterator("KMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        characterIterator.next()
    }

    return "%.1f %c".format(bytes / 1000.0, characterIterator.current())
}
