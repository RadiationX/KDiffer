package ru.radiationx.kdiffer.internal.visitor

import ru.radiationx.kdiffer.dsl.field.ModelFieldContext
import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.field.ModelFieldSelector
import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.delegate.MutableFieldVisitorDelegate
import ru.radiationx.kdiffer.internal.visitor.mapper.ModelVisitorResultMapper

internal class MutableModelFieldVisitorImpl<Model, Field>(
    selector: ModelFieldSelector<Model, Field>
) : ModelFieldContext<Model, Field>, ModelFieldVisitor<Model> {

    private val mapper = ModelVisitorResultMapper(selector)
    private val delegate = MutableFieldVisitorDelegate(selector, mapper)

    override fun visit(modelScope: ValueScope<Model>) = delegate.visit(modelScope)

    override fun setField(field: DifferField<Model, Field>) = delegate.addField(field)

    override fun clear() = delegate.clear()
}