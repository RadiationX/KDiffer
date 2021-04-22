package ru.radiationx.kdiffer.internal.differ.delegate

import ru.radiationx.kdiffer.differ.StatelessDiffer
import ru.radiationx.kdiffer.internal.differ.mapper.DifferResultMapper
import ru.radiationx.kdiffer.internal.scopes.ValueScope

internal class StatelessDifferDelegate<Model, VisitorResult, Result>(
    private val mapper: DifferResultMapper<Model, VisitorResult, Result>
) : DifferDelegate<Model, VisitorResult, Result>(), StatelessDiffer<Model, Result> {

    override fun accept(oldValue: Model?, newValue: Model): Result {
        val scope = ValueScope(oldValue, newValue)
        return mapper.map(scope, visitors)
    }
}