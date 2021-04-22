package ru.radiationx.kdiffer.dsl.field

import ru.radiationx.kdiffer.field.DifferField

interface LiveFieldContext<Model, Field> {

    fun addField(field: DifferField<Model, Field>)
}