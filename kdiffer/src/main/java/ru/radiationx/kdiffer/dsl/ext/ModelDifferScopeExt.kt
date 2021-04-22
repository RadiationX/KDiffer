package ru.radiationx.kdiffer.dsl.ext

import ru.radiationx.kdiffer.detector.DiffDetector
import ru.radiationx.kdiffer.detector.EqualDiffDetector
import ru.radiationx.kdiffer.detector.IgnoreDiffDetector
import ru.radiationx.kdiffer.detector.RefDiffDetector
import ru.radiationx.kdiffer.dsl.differ.ModelDifferContext
import ru.radiationx.kdiffer.dsl.field.ModelFieldContext
import ru.radiationx.kdiffer.field.ModelFieldSelector

fun <Model, Field> ModelDifferContext<Model>.value(
    name: String,
    diffDetector: DiffDetector<Field> = EqualDiffDetector(),
    provider: ValueProvider<Model, Field>
): ModelFieldContext<Model, Field> {
    val selector = ModelFieldSelector(name, diffDetector, provider)
    return addSelector(selector)
}

fun <Model, Field> ModelDifferContext<Model>.ref(
    name: String,
    diffDetector: DiffDetector<Field> = RefDiffDetector(),
    provider: ValueProvider<Model, Field>
): ModelFieldContext<Model, Field> {
    val selector = ModelFieldSelector(name, diffDetector, provider)
    return addSelector(selector)
}

fun <Model, Field> ModelDifferContext<Model>.any(
    name: String,
    diffDetector: DiffDetector<Field> = IgnoreDiffDetector(),
    provider: ValueProvider<Model, Field>
): ModelFieldContext<Model, Field> {
    val selector = ModelFieldSelector(name, diffDetector, provider)
    return addSelector(selector)
}