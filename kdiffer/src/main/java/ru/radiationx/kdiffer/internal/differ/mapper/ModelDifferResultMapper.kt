package ru.radiationx.kdiffer.internal.differ.mapper

import ru.radiationx.kdiffer.dsl.ext.DifferModelResult
import ru.radiationx.kdiffer.internal.FieldModelVisitResult
import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.FieldVisitor

internal class ModelDifferResultMapper<Model> :
    DifferResultMapper<Model, FieldModelVisitResult, DifferModelResult> {

    override fun map(
        scope: ValueScope<Model>,
        visitors: Collection<FieldVisitor<Model, FieldModelVisitResult>>
    ): DifferModelResult {
        return visitors.mapNotNull { it.visit(scope) }.toMap()
    }
}