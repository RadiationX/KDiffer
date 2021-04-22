package ru.radiationx.kdiffer.internal.visitor

import ru.radiationx.kdiffer.common.Cleanable
import ru.radiationx.kdiffer.dsl.field.LiveFieldContext
import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.field.LiveFieldSelector
import ru.radiationx.kdiffer.internal.scopes.ValueScope
import ru.radiationx.kdiffer.internal.visitor.delegate.StatelessFieldVisitorDelegate
import ru.radiationx.kdiffer.internal.visitor.mapper.LiveVisitorResultMapper

internal class StatelessLiveFieldVisitorImpl<Model, Field>(
    selector: LiveFieldSelector<Model, Field>
) : LiveFieldContext<Model, Field>, Cleanable, LiveFieldVisitor<Model> {

    private val mapper = LiveVisitorResultMapper<Model, Field>()
    private val delegate = StatelessFieldVisitorDelegate(selector, mapper)

    override fun visit(modelScope: ValueScope<Model>) {
        delegate.visit(modelScope)
    }

    override fun addField(field: DifferField<Model, Field>) = delegate.addField(field)

    override fun clear() = delegate.clear()
}