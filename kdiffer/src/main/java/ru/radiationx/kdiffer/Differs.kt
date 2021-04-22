package ru.radiationx.kdiffer

import ru.radiationx.kdiffer.differ.MutableLiveDiffer
import ru.radiationx.kdiffer.differ.MutableModelDiffer
import ru.radiationx.kdiffer.differ.StatelessLiveDiffer
import ru.radiationx.kdiffer.differ.StatelessModelDiffer
import ru.radiationx.kdiffer.dsl.differ.LiveDifferContext
import ru.radiationx.kdiffer.dsl.differ.ModelDifferContext
import ru.radiationx.kdiffer.internal.differ.MutableLiveDifferImpl
import ru.radiationx.kdiffer.internal.differ.MutableModelDifferImpl
import ru.radiationx.kdiffer.internal.differ.StatelessLiveDifferImpl
import ru.radiationx.kdiffer.internal.differ.StatelessModelDifferImpl

fun <Model> mutableModelDiffer(
    block: ModelDifferContext<Model>.() -> Unit
): MutableModelDiffer<Model> = MutableModelDifferImpl<Model>().apply(block)

fun <Model> statelessModelDiffer(
    block: ModelDifferContext<Model>.() -> Unit
): StatelessModelDiffer<Model> = StatelessModelDifferImpl<Model>().apply(block)

fun <Model> mutableLiveDiffer(
    block: LiveDifferContext<Model>.() -> Unit
): MutableLiveDiffer<Model> = MutableLiveDifferImpl<Model>().apply(block)

fun <Model> statelessLiveDiffer(
    block: LiveDifferContext<Model>.() -> Unit
): StatelessLiveDiffer<Model> = StatelessLiveDifferImpl<Model>().apply(block)


