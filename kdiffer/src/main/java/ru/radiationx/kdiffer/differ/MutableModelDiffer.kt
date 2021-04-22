package ru.radiationx.kdiffer.differ

import ru.radiationx.kdiffer.dsl.ext.DifferModelResult

interface MutableModelDiffer<Model> : MutableDiffer<Model, DifferModelResult>