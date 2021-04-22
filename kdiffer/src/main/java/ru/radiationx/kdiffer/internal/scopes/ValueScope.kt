package ru.radiationx.kdiffer.internal.scopes

import ru.radiationx.kdiffer.dsl.scope.ValueProviderScope

internal class ValueScope<T>(old: T?, new: T) : ValueProviderScope<T> {

    override var old: T? = old
        private set

    override var new: T = new
        private set

    internal inline fun <R> update(newValue: T, crossinline block: () -> R): R {
        new = newValue
        val result = block.invoke()
        old = new
        return result
    }
}