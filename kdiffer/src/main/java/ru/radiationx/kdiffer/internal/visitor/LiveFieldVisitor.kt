package ru.radiationx.kdiffer.internal.visitor

import ru.radiationx.kdiffer.internal.FieldLiveVisitResult

internal interface LiveFieldVisitor<Model> : FieldVisitor<Model, FieldLiveVisitResult>