package ru.radiationx.kdiffer.differ

import ru.radiationx.kdiffer.dsl.ext.DifferModelResult

interface StatelessModelDiffer<Model> : StatelessDiffer<Model, DifferModelResult>