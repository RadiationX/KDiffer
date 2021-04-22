package ru.radiationx.kdiffer.dsl.ext

import ru.radiationx.kdiffer.differ.MutableLiveDiffer
import ru.radiationx.kdiffer.differ.StatelessLiveDiffer
import ru.radiationx.kdiffer.dsl.differ.LiveDifferContext
import ru.radiationx.kdiffer.dsl.field.LiveFieldContext
import ru.radiationx.kdiffer.field.DifferField
import ru.radiationx.kdiffer.statelessLiveDiffer
import ru.radiationx.kdiffer.mutableLiveDiffer

inline infix fun <Model, Field> LiveFieldContext<Model, Field>.withField(block: LiveFieldContext<Model, Field>.() -> Unit) {
    block()
}

infix fun <Model, Field> LiveFieldContext<Model, Field>.call(block: LiveFieldCaller<Model, Field>) {
    addField(DifferField(null, block))
}

fun <Model, Field> LiveFieldContext<Model, Field>.registerMutableDiffer(differ: MutableLiveDiffer<Field>) {
    addField(DifferField(differ) {
        differ.accept(it)
    })
}

fun <Model, Field> LiveFieldContext<Model, Field>.registerStatelessDiffer(differ: StatelessLiveDiffer<Field>) {
    addField(DifferField(differ) {
        differ.accept(field.old, field.new)
    })
}

infix fun <Model, Field> LiveFieldContext<Model, Field>.withMutableDiffer(block: LiveDifferContext<Field>.() -> Unit) {
    val differ = mutableLiveDiffer(block)
    registerMutableDiffer(differ)
}

infix fun <Model, Field> LiveFieldContext<Model, Field>.withStatelessDiffer(block: LiveDifferContext<Field>.() -> Unit) {
    val differ = statelessLiveDiffer(block)
    registerStatelessDiffer(differ)
}
