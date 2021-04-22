package ru.radiationx.kdiffer.internal.scopes

import ru.radiationx.kdiffer.dsl.scope.FieldCallerScope

internal class FieldScope<Model, Field>(
    parent: ValueScope<Model>,
    field: ValueScope<Field>
) : FieldCallerScope<Model, Field> {

    override var parent: ValueScope<Model> = parent
        internal set

    override var field: ValueScope<Field> = field
        internal set
}