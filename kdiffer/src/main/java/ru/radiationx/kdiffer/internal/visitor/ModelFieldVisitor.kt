package ru.radiationx.kdiffer.internal.visitor

import ru.radiationx.kdiffer.internal.FieldModelVisitResult

internal interface ModelFieldVisitor<Model> : FieldVisitor<Model, FieldModelVisitResult>