package ru.radiationx.kdiffer.field

import ru.radiationx.kdiffer.dsl.ext.ValueProvider
import ru.radiationx.kdiffer.detector.DiffDetector

class ModelFieldSelector<Model, Field>(
    val name: String,
    diffDetector: DiffDetector<Field>,
    provider: ValueProvider<Model, Field>
) : FieldSelector<Model, Field>(diffDetector, provider)