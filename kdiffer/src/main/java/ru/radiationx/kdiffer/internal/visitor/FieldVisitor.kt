package ru.radiationx.kdiffer.internal.visitor

import ru.radiationx.kdiffer.common.Cleanable
import ru.radiationx.kdiffer.internal.scopes.ValueScope

internal interface FieldVisitor<Model, Result> : Cleanable {
    fun visit(modelScope: ValueScope<Model>): Result
}