package ru.radiationx.kdiffer.internal.differ.mapper

import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.FieldVisitor

internal interface DifferResultMapper<Model, VisitorResult, Result> {

    fun map(
        scope: ValueScope<Model>,
        visitors: Collection<FieldVisitor<Model, VisitorResult>>
    ): Result
}

