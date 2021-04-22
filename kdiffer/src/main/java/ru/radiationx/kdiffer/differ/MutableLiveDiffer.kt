package ru.radiationx.kdiffer.differ

import ru.radiationx.kdiffer.dsl.ext.DifferLiveResult

interface MutableLiveDiffer<Model> : MutableDiffer<Model, DifferLiveResult>