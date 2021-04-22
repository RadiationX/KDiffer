package ru.radiationx.kdiffer.internal.differ

import ru.radiationx.kdiffer.differ.StatelessModelDiffer
import ru.radiationx.kdiffer.dsl.differ.ModelDifferContext
import ru.radiationx.kdiffer.dsl.field.ModelFieldContext
import ru.radiationx.kdiffer.field.ModelFieldSelector
import ru.radiationx.kdiffer.internal.differ.delegate.StatelessDifferDelegate
import ru.radiationx.kdiffer.internal.differ.mapper.ModelDifferResultMapper
import ru.radiationx.kdiffer.internal.visitor.StatelessModelFieldVisitorImpl

internal class StatelessModelDifferImpl<Model> : ModelDifferContext<Model>,
    StatelessModelDiffer<Model> {

    private val delegate = StatelessDifferDelegate(ModelDifferResultMapper<Model>())

    override fun accept(oldValue: Model?, newValue: Model) = delegate.accept(oldValue, newValue)

    override fun clear() = delegate.clear()

    override fun <Field> addSelector(
        selector: ModelFieldSelector<Model, Field>
    ): ModelFieldContext<Model, Field> {
        val visitor = StatelessModelFieldVisitorImpl(selector)
        delegate.addVisitor(visitor)
        return visitor
    }

    override fun onClear(block: () -> Unit) = delegate.onClear(block)
}