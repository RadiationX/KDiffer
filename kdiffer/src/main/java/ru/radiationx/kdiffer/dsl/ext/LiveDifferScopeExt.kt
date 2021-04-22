package ru.radiationx.kdiffer.dsl.ext

import ru.radiationx.kdiffer.detector.DiffDetector
import ru.radiationx.kdiffer.detector.EqualDiffDetector
import ru.radiationx.kdiffer.detector.IgnoreDiffDetector
import ru.radiationx.kdiffer.detector.RefDiffDetector
import ru.radiationx.kdiffer.dsl.differ.LiveDifferContext
import ru.radiationx.kdiffer.dsl.field.LiveFieldContext
import ru.radiationx.kdiffer.field.LiveFieldSelector

fun <Model, Field> LiveDifferContext<Model>.value(
    diffDetector: DiffDetector<Field> = EqualDiffDetector(),
    provider: ValueProvider<Model, Field>
): LiveFieldContext<Model, Field> {
    val selector = LiveFieldSelector(diffDetector, provider)
    return addSelector(selector)
}

fun <Model, Field> LiveDifferContext<Model>.ref(
    diffDetector: DiffDetector<Field> = RefDiffDetector(),
    provider: ValueProvider<Model, Field>
): LiveFieldContext<Model, Field> {
    val selector = LiveFieldSelector(diffDetector, provider)
    return addSelector(selector)
}

fun <Model, Field> LiveDifferContext<Model>.any(
    diffDetector: DiffDetector<Field> = IgnoreDiffDetector(),
    provider: ValueProvider<Model, Field>
): LiveFieldContext<Model, Field> {
    val selector = LiveFieldSelector(diffDetector, provider)
    return addSelector(selector)
}