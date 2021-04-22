package ru.radiationx.kdiffer.internal.visitor.delegate

import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.field.FieldSelector
import ru.radiationx.kdiffer.internal.scopes.FieldScope
import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.mapper.VisitorResultMapper

internal class StatelessFieldVisitorDelegate<Model, Field, Result, DF : DifferField<Model, Field>>(
    private val selector: FieldSelector<Model, Field>,
    private val mapper: VisitorResultMapper<Model, Field, Result, DF>
) : FieldVisitorDelegate<Model, Field, Result, DF>() {

    fun visit(modelScope: ValueScope<Model>): Result? {
        val oldValue = modelScope.old?.let { selector.valueProvider.invoke(modelScope, it) }
        val newValue = selector.valueProvider.invoke(modelScope, modelScope.new)

        val hasChanges = selector.diffDetector.invoke(oldValue, newValue)
        return if (hasChanges) {
            val scope = FieldScope(modelScope, ValueScope(oldValue, newValue))
            mapper.map(scope, differFields, newValue)
        } else {
            null
        }
    }
}