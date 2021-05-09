package ru.radiationx.kdiffer.internal.visitor.delegate

import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.field.FieldSelector
import ru.radiationx.kdiffer.internal.scopes.FieldScope
import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.mapper.VisitorResultMapper

internal class MutableFieldVisitorDelegate<Model, Field, Result, DF : DifferField<Model, Field>>(
    private val selector: FieldSelector<Model, Field>,
    private val mapper: VisitorResultMapper<Model, Field, Result, DF>
) : FieldVisitorDelegate<Model, Field, Result, DF>() {

    private var fieldScope: FieldScope<Model, Field>? = null

    fun visit(modelScope: ValueScope<Model>): Result? {
        val newValue = selector.valueProvider.invoke(modelScope, modelScope.new)

        val noScope = fieldScope == null
        val scope = fieldScope ?: FieldScope(modelScope, ValueScope(null, newValue))
        fieldScope = scope
        return scope.field.update(newValue) {
            val hasChanges = noScope || selector.diffDetector.invoke(scope.field.old, newValue)

            if (hasChanges) {
                mapper.map(scope, differFields, newValue)
            } else {
                null
            }
        }
    }

    override fun clear() {
        super.clear()
        fieldScope = null
    }
}