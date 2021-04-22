package ru.radiationx.kdiffer.dsl.ext

import ru.radiationx.kdiffer.dsl.scope.FieldCallerScope
import ru.radiationx.kdiffer.dsl.scope.ValueProviderScope

typealias ValueProvider<Model, Field> = ValueProviderScope<Model>.(Model) -> Field
typealias ModelFieldCaller<Model, Field> = FieldCallerScope<Model, Field>.(Field) -> Any?
typealias LiveFieldCaller<Model, Field> = FieldCallerScope<Model, Field>.(Field) -> Unit

typealias DifferModelResult = Map<String, *>
typealias DifferLiveResult = Unit
