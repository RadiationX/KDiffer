package ru.radiationx.kdiffer.field

import ru.radiationx.kdiffer.dsl.ext.ValueProvider
import ru.radiationx.kdiffer.detector.DiffDetector

open class FieldSelector<Model, Field>(
    val diffDetector: DiffDetector<Field>,
    val valueProvider: ValueProvider<Model, Field>
)