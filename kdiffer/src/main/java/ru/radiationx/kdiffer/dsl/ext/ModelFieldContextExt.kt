package ru.radiationx.kdiffer.dsl.ext

import ru.radiationx.kdiffer.differ.MutableModelDiffer
import ru.radiationx.kdiffer.differ.StatelessModelDiffer
import ru.radiationx.kdiffer.dsl.differ.ModelDifferContext
import ru.radiationx.kdiffer.dsl.field.ModelFieldContext
import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.statelessModelDiffer
import ru.radiationx.kdiffer.mutableModelDiffer

infix fun <Model, Field> ModelFieldContext<Model, Field>.map(block: ModelFieldCaller<Model, Field>) {
    setField(DifferField(null, block))
}

fun <Model, Field> ModelFieldContext<Model, Field>.registerMutableDiffer(differ: MutableModelDiffer<Field>) {
    setField(DifferField(differ) {
        differ.accept(it)
    })
}

fun <Model, Field> ModelFieldContext<Model, Field>.registerStatelessDiffer(differ: StatelessModelDiffer<Field>) {
    setField(DifferField(differ) {
        differ.accept(field.old, field.new)
    })
}

infix fun <Model, Field> ModelFieldContext<Model, Field>.withMutableDiffer(block: ModelDifferContext<Field>.() -> Unit) {
    val differ = mutableModelDiffer(block)
    registerMutableDiffer(differ)
}

infix fun <Model, Field> ModelFieldContext<Model, Field>.withStatelessDiffer(block: ModelDifferContext<Field>.() -> Unit) {
    val differ = statelessModelDiffer(block)
    registerStatelessDiffer(differ)
}