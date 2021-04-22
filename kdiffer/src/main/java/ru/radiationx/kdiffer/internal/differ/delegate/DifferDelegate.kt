package ru.radiationx.kdiffer.internal.differ.delegate

import ru.radiationx.kdiffer.common.Cleanable
import ru.radiationx.kdiffer.dsl.common.CleanableContext
import ru.radiationx.kdiffer.internal.visitor.FieldVisitor
import java.util.*

internal abstract class DifferDelegate<Model, VisitorResult, Result> : CleanableContext, Cleanable {

    protected val clearingListeners = mutableListOf<() -> Unit>()
    protected val visitors = LinkedList<FieldVisitor<Model, VisitorResult>>()

    override fun clear() {
        visitors.forEach { it.clear() }
        visitors.clear()
        clearingListeners.forEach { it.invoke() }
        clearingListeners.clear()
    }

    fun addVisitor(visitor: FieldVisitor<Model, VisitorResult>) {
        visitors.add(visitor)
    }

    override fun onClear(block: () -> Unit) {
        clearingListeners.add(block)
    }
}