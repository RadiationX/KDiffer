package ru.radiationx.kdiffer.internal.visitor.delegate

import ru.radiationx.kdiffer.common.Cleanable
import ru.radiationx.kdiffer.field.DifferField
import java.util.*

internal abstract class FieldVisitorDelegate<Model, Field, Result, DF : DifferField<Model, Field>> :
    Cleanable {

    protected val differFields = LinkedList<DF>()

    override fun clear() {
        differFields.forEach { it.clear() }
        differFields.clear()
    }

    fun addField(field: DF) {
        differFields.add(field)
    }
}