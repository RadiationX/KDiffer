package ru.radiationx.kdiffer.detector

class RefDiffDetector<T> : DiffDetector<T> {

    override fun invoke(oldValue: T?, newValue: T): Boolean = oldValue !== newValue
}