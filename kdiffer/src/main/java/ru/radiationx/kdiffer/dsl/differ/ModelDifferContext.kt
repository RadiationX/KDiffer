package ru.radiationx.kdiffer.dsl.differ

import ru.radiationx.kdiffer.dsl.common.CleanableContext
import ru.radiationx.kdiffer.dsl.field.ModelFieldContext
import ru.radiationx.kdiffer.field.ModelFieldSelector

interface ModelDifferContext<Model> : CleanableContext {

    fun <Field> addSelector(selector: ModelFieldSelector<Model, Field>): ModelFieldContext<Model, Field>
}