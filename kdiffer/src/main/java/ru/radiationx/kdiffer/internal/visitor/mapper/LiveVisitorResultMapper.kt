package ru.radiationx.kdiffer.internal.visitor.mapper

import ru.radiationx.kdiffer.dsl.ext.DifferLiveResult
import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.internal.scopes.FieldScope

internal class LiveVisitorResultMapper<Model, Field> :
    VisitorResultMapper<Model, Field, DifferLiveResult, DifferField<Model, Field>> {

    override fun map(
        scope: FieldScope<Model, Field>,
        differFields: Collection<DifferField<Model, Field>>,
        newValue: Field
    ) {
        differFields.forEach {
            it.caller.invoke(scope, newValue)
        }
    }
}