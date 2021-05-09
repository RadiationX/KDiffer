package ru.radiationx.kdiffersample

import org.junit.Test
import ru.radiationx.kdiffer.dsl.ext.call
import ru.radiationx.kdiffer.dsl.ext.map
import ru.radiationx.kdiffer.dsl.ext.value
import ru.radiationx.kdiffer.dsl.ext.withMutableDiffer
import ru.radiationx.kdiffer.mutableLiveDiffer
import ru.radiationx.kdiffer.mutableModelDiffer
import ru.radiationx.kdiffersample.model.Model
import ru.radiationx.kdiffersample.model.SubModel

class ProMappingSamples {


    @Test
    fun liveDifferSample() {
        val differ = mutableLiveDiffer<Model> {
            value {
                // here we can get old and new model
                println("get title field on model '$old' -> '$new'")
                it.title
            } call {
                // here we can get old and new model, old and new field value
                println("title diff: parent '${parent.old}' -> '${parent.new}'")
                println("title diff: field '${field.old}' -> '${field.new}'")
            }
        }

        val initialModel = Model("Title1", SubModel("text1", 1))
        val model2 = initialModel.copy(title = "Title2")
        val model3 = model2.copy()

        println()
        println("sample: 1 accept")
        differ.accept(initialModel)

        println()
        println("sample: 2 accept")
        differ.accept(model2)

        println()
        println("sample: 3 accept")
        differ.accept(model3)
    }

    @Test
    fun modelDifferSample() {
        val differ = mutableModelDiffer<Model> {
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
    }
}