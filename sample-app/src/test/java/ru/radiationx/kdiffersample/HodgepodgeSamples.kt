package ru.radiationx.kdiffersample

import org.junit.Test
import ru.radiationx.kdiffer.dsl.ext.*
import ru.radiationx.kdiffer.mutableLiveDiffer
import ru.radiationx.kdiffer.mutableModelDiffer
import ru.radiationx.kdiffer.statelessLiveDiffer
import ru.radiationx.kdiffer.statelessModelDiffer
import ru.radiationx.kdiffersample.model.Model
import ru.radiationx.kdiffersample.model.SubModel

class HodgepodgeSamples {

    @Test
    fun liveDifferSample() {
        val subStatelessDiffer = statelessLiveDiffer<SubModel> {
            value { it.text } call {
                println("external stateless differ: call value Model.sub.text $it")
            }
            value { it.size } call {
                println("external stateless differ: call value Model.sub.size $it")
            }
            onClear {
                println("external stateless differ: clear")
            }
        }

        val differ = mutableLiveDiffer<Model> {
            value { it.title } call {
                println("call value Model.title $it")
            }
            ref { it.title } call {
                println("call ref Model.title $it")
            }
            any { it.title } call {
                println("call any Model.title $it")
            }

            value { it.sub.text } call {
                println("call value Model.sub.text $it")
            }
            value { it.sub.size } call {
                println("call value Model.sub.size $it")
            }

            value { it.sub } withMutableDiffer {
                value { it.text } call {
                    println("inner mutable differ: call value Model.sub.text $it")
                }
                value { it.size } call {
                    println("inner mutable differ: call value Model.sub.size $it")
                }
                onClear {
                    println("inner mutable differ: clear")
                }
            }

            value { it.sub }.registerStatelessDiffer(subStatelessDiffer)

            value { it.title } call { println("title diff: ${field.old} -> ${field.new}") }

            onClear {
                println("call clear")
            }
        }

        val initialModel = Model("Title1", SubModel("text1", 1))
        val model2 = initialModel.copy(title = "Title2")
        val model3 = model2.copy(sub = model2.sub.copy(text = "text2"))

        println()
        println("sample: 1 accept")
        differ.accept(initialModel)

        println()
        println("sample: 2 accept")
        differ.accept(model2)

        println()
        println("sample: 3 accept")
        differ.accept(model3)

        println()
        println("sample: clear")
        differ.clear()
    }

    @Test
    fun modelDifferSample() {
        val subStatelessDiffer = statelessModelDiffer<SubModel> {
            value("text") { it.text }
            value("size") { it.size }
            onClear {
                println("external stateless differ: clear")
            }
        }

        val differ = mutableModelDiffer<Model> {
            value("title") { it.title }
            ref("title") { it.title }
            any("title") { it.title }

            value("flat_subtext") { it.sub.text }
            value("flat_subsize") { it.sub.size }

            value("sub_with") { it.sub } withMutableDiffer {
                value("text") { it.text }
                value("size") { it.size }
                onClear {
                    println("inner mutable differ: clear")
                }
            }

            value("sub_register") { it.sub }.registerStatelessDiffer(subStatelessDiffer)

            value("title_diff_map") { it.title } map {
                mapOf(
                    "old" to field.old,
                    "new" to field.new
                )
            }

            value("title_diff_differ") { it.title } withMutableDiffer {
                value("old") { old }
                value("new") { new }
            }



            onClear {
                println("call clear")
            }
        }

        val initialModel = Model("Title1", SubModel("text1", 1))
        val model2 = initialModel.copy(title = "Title2")
        val model3 = model2.copy(sub = model2.sub.copy(text = "text2"))

        println()
        println("sample: 1 accept")
        println(differ.accept(initialModel))

        println()
        println("sample: 2 accept")
        println(differ.accept(model2))

        println()
        println("sample: 3 accept")
        println(differ.accept(model3))

        println()
        println("sample: clear")
        differ.clear()
    }
}