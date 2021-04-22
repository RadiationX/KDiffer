package ru.radiationx.kdiffer.field

import ru.radiationx.kdiffer.dsl.ext.ValueProvider
import ru.radiationx.kdiffer.detector.DiffDetector

class LiveFieldSelector<Model, Field>(
    diffDetector: DiffDetector<Field>,
    provider: ValueProvider<Model, Field>
) : FieldSelector<Model, Field>(diffDetector, provider)