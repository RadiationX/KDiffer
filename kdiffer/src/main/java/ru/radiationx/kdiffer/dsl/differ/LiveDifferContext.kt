package ru.radiationx.kdiffer.dsl.differ

import ru.radiationx.kdiffer.dsl.common.CleanableContext
import ru.radiationx.kdiffer.dsl.field.LiveFieldContext
import ru.radiationx.kdiffer.field.LiveFieldSelector

interface LiveDifferContext<Model> : CleanableContext {

    fun <Field> addSelector(selector: LiveFieldSelector<Model, Field>): LiveFieldContext<Model, Field>
}