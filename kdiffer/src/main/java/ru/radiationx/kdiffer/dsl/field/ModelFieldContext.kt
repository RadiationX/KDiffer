package ru.radiationx.kdiffer.dsl.field

import ru.radiationx.kdiffer.field.DifferField

interface ModelFieldContext<Model, Field> {

    fun setField(field: DifferField<Model, Field>)
}