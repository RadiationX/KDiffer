package ru.radiationx.kdiffer.internal.differ

import ru.radiationx.kdiffer.differ.MutableModelDiffer
import ru.radiationx.kdiffer.dsl.differ.ModelDifferContext
import ru.radiationx.kdiffer.dsl.field.ModelFieldContext
import ru.radiationx.kdiffer.field.ModelFieldSelector
import ru.radiationx.kdiffer.internal.differ.delegate.MutableDifferDelegate
import ru.radiationx.kdiffer.internal.differ.mapper.ModelDifferResultMapper
import ru.radiationx.kdiffer.internal.visitor.MutableModelFieldVisitorImpl

internal class MutableModelDifferImpl<Model> : ModelDifferContext<Model>,
    MutableModelDiffer<Model> {

    private val delegate = MutableDifferDelegate(ModelDifferResultMapper<Model>())

    override fun accept(value: Model) = delegate.accept(value)

    override fun clear() = delegate.clear()

    override fun <Field> addSelector(
        selector: ModelFieldSelector<Model, Field>
    ): ModelFieldContext<Model, Field> {
        val visitor = MutableModelFieldVisitorImpl(selector)
        delegate.addVisitor(visitor)
        return visitor
    }

    override fun onClear(block: () -> Unit) = delegate.onClear(block)
}