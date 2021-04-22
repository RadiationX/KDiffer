package ru.radiationx.kdiffer.dsl.scope

interface ValueProviderScope<T> {
    val old: T?
    val new: T
}