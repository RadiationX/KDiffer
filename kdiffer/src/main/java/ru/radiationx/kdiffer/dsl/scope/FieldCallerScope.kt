package ru.radiationx.kdiffer.dsl.scope

interface FieldCallerScope<Model, Field> {
    val parent: ValueProviderScope<Model>
    val field: ValueProviderScope<Field>
}
