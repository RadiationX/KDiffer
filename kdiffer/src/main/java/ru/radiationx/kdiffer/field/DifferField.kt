package ru.radiationx.kdiffer.field

import ru.radiationx.kdiffer.common.Cleanable
import ru.radiationx.kdiffer.dsl.ext.ModelFieldCaller

class DifferField<Model, Field>(
    val cleanable: Cleanable?,
    val caller: ModelFieldCaller<Model, Field>
) : Cleanable {

    override fun clear() {
        cleanable?.clear()
    }
}