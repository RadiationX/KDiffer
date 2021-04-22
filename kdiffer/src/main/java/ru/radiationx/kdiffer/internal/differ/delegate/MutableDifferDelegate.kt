package ru.radiationx.kdiffer.internal.differ.delegate

import ru.radiationx.kdiffer.differ.MutableDiffer
import ru.radiationx.kdiffer.internal.differ.mapper.DifferResultMapper
import ru.radiationx.kdiffer.internal.scopes.ValueScope

internal class MutableDifferDelegate<Model, VisitorResult, Result>(
    private val mapper: DifferResultMapper<Model, VisitorResult, Result>
) : DifferDelegate<Model, VisitorResult, Result>(), MutableDiffer<Model, Result> {

    private var scope: ValueScope<Model>? = null

    override fun accept(value: Model): Result {
        val scope = scope ?: ValueScope(null, value)
        this.scope = scope
        return scope.update(value) {
            mapper.map(scope, visitors)
        }
    }

    override fun clear() {
        super.clear()
        scope = null
    }
}