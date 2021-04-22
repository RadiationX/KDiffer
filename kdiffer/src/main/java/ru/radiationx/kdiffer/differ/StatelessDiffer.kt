package ru.radiationx.kdiffer.differ

import ru.radiationx.kdiffer.common.Cleanable

interface StatelessDiffer<Model, Result> : Cleanable {
    fun accept(oldValue: Model?, newValue: Model): Result
}