package ru.radiationx.kdiffer.internal.visitor.mapper

import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.field.ModelFieldSelector
import ru.radiationx.kdiffer.internal.FieldModelVisitResult
import ru.radiationx.kdiffer.internal.scopes.FieldScope

internal class ModelVisitorResultMapper<Model, Field>(
    private val selector: ModelFieldSelector<Model, Field>
) : VisitorResultMapper<Model, Field, FieldModelVisitResult, DifferField<Model, Field>> {

    override fun map(
        scope: FieldScope<Model, Field>,
        differFields: Collection<DifferField<Model, Field>>,
        newValue: Field
    ): FieldModelVisitResult {
        val mappedValue = differFields.takeIf { it.isNotEmpty() }?.let {
            differFields.lastOrNull()?.caller?.invoke(scope, newValue)
        }
        return Pair(selector.name, mappedValue ?: newValue)
    }
}