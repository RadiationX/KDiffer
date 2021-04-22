package ru.radiationx.kdiffer.internal.differ

import ru.radiationx.kdiffer.differ.StatelessLiveDiffer
import ru.radiationx.kdiffer.dsl.differ.LiveDifferContext
import ru.radiationx.kdiffer.dsl.field.LiveFieldContext
import ru.radiationx.kdiffer.field.LiveFieldSelector
import ru.radiationx.kdiffer.internal.differ.delegate.StatelessDifferDelegate
import ru.radiationx.kdiffer.internal.differ.mapper.LiveDifferResultMapper
import ru.radiationx.kdiffer.internal.visitor.StatelessLiveFieldVisitorImpl

internal class StatelessLiveDifferImpl<Model> : LiveDifferContext<Model>,
    StatelessLiveDiffer<Model> {

    private val delegate = StatelessDifferDelegate(LiveDifferResultMapper<Model>())

    override fun accept(oldValue: Model?, newValue: Model) {
        delegate.accept(oldValue, newValue)
    }

    override fun clear() = delegate.clear()

    override fun <Field> addSelector(
        selector: LiveFieldSelector<Model, Field>
    ): LiveFieldContext<Model, Field> {
        val visitor = StatelessLiveFieldVisitorImpl(selector)
        delegate.addVisitor(visitor)
        return visitor
    }

    override fun onClear(block: () -> Unit) = delegate.onClear(block)
}