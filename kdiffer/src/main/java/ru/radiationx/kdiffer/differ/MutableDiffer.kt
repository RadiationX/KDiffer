package ru.radiationx.kdiffer.differ

import ru.radiationx.kdiffer.common.Cleanable

interface MutableDiffer<Model, Result> : Cleanable {
    fun accept(value: Model): Result
}