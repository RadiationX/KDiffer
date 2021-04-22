package ru.radiationx.kdiffer.internal.differ

import ru.radiationx.kdiffer.differ.MutableLiveDiffer
import ru.radiationx.kdiffer.dsl.differ.LiveDifferContext
import ru.radiationx.kdiffer.dsl.field.LiveFieldContext
import ru.radiationx.kdiffer.field.LiveFieldSelector
import ru.radiationx.kdiffer.internal.differ.delegate.MutableDifferDelegate
import ru.radiationx.kdiffer.internal.differ.mapper.LiveDifferResultMapper
import ru.radiationx.kdiffer.internal.visitor.MutableLiveFieldVisitorImpl

internal class MutableLiveDifferImpl<Model> : LiveDifferContext<Model>, MutableLiveDiffer<Model> {

    private val delegate = MutableDifferDelegate(LiveDifferResultMapper<Model>())

    override fun accept(value: Model) {
        delegate.accept(value)
    }

    override fun clear() = delegate.clear()

    override fun <Field> addSelector(
        selector: LiveFieldSelector<Model, Field>
    ): LiveFieldContext<Model, Field> {
        val visitor = MutableLiveFieldVisitorImpl(selector)
        delegate.addVisitor(visitor)
        return visitor
    }

    override fun onClear(block: () -> Unit) = delegate.onClear(block)
}