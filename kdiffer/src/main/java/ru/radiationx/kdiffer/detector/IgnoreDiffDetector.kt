package ru.radiationx.kdiffer.detector

class IgnoreDiffDetector<T> : DiffDetector<T> {

    override fun invoke(oldValue: T?, newValue: T): Boolean = true
}