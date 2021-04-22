package ru.radiationx.kdiffer.internal.visitor.mapper

import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.internal.scopes.FieldScope

internal interface VisitorResultMapper<Model, Field, Result, DF : DifferField<Model, Field>> {

    fun map(scope: FieldScope<Model, Field>, differFields: Collection<DF>, newValue: Field): Result
}