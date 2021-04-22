package ru.radiationx.kdiffer.differ

import ru.radiationx.kdiffer.dsl.ext.DifferLiveResult

interface StatelessLiveDiffer<Model> : StatelessDiffer<Model, DifferLiveResult>