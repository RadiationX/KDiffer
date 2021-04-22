package ru.radiationx.kdiffer.detector

fun interface DiffDetector<T> {

    operator fun invoke(oldValue: T?, newValue: T): Boolean
}