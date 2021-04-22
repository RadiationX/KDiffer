package ru.radiationx.kdiffer.internal.differ.mapper

import ru.radiationx.kdiffer.dsl.ext.DifferLiveResult
import ru.radiationx.kdiffer.internal.FieldLiveVisitResult
import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.FieldVisitor

internal class LiveDifferResultMapper<Model> :
    DifferResultMapper<Model, FieldLiveVisitResult, DifferLiveResult> {

    override fun map(
        scope: ValueScope<Model>,
        visitors: Collection<FieldVisitor<Model, FieldLiveVisitResult>>
    ) {
        visitors.forEach { it.visit(scope) }
    }
}