package ru.radiationx.kdiffersample

import ru.radiationx.kdiffer.dsl.ext.*
import ru.radiationx.kdiffer.mutableLiveDiffer
import ru.radiationx.kdiffer.statelessLiveDiffer
import java.util.*

class LiveDifferSamples {

    data class Model(val date: Date, val sub: SubModel)

    data class SubModel(val text: String, val size: Int)

    fun basic() {
        val subMutableDiffer = mutableLiveDiffer<SubModel> {
            value { it.text } call { /* some action */ }
            value { it.size } call { /* some action */ }
            onClear { /* some action */ }
        }
        val subStatelessDiffer = statelessLiveDiffer<SubModel> {

        }

        val differ = mutableLiveDiffer<Model> {

            // call when "date" equals == false
            value { it.date } call { /* some action */ }

            // call when "date" references are different
            ref { it.date } call { /* some action */ }

            // call always after accept new model
            any { it.date } call { /* some action */ }

            // of course also possible select nested fields
            value { it.sub.text } call { /* some action */ }
            value { it.sub.size } call { /* some action */ }

            // differ for field. inner field selectors will call only if "sub" changed
            value { it.sub } withMutableDiffer {
                value { it.text } call { /* some action */ }
                value { it.size } call { /* some action */ }
                onClear { /* some action */ }
            }
            value { it.sub } withStatelessDiffer {
                value { it.text } call { /* some action */ }
                value { it.size } call { /* some action */ }
                onClear { /* some action */ }
            }

            // also can add differ instance
            // !!! differ instance also will clear, after clear "parent" differ
            value { it.sub }.registerMutableDiffer(subMutableDiffer)
            value { it.sub }.registerStatelessDiffer(subStatelessDiffer)

            // call when clear() differ
            onClear { /* some action */ }
        }
    }
}