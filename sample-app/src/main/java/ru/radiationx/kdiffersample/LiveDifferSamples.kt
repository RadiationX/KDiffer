package ru.radiationx.kdiffersample

import android.util.Log
import ru.radiationx.kdiffer.dsl.ext.*
import ru.radiationx.kdiffer.mutableLiveDiffer
import ru.radiationx.kdiffer.statelessLiveDiffer
import java.util.*

class LiveDifferSamples {

    private val TAG = LiveDifferSamples::class.simpleName

    data class Model(val date: Date, val sub: SubModel)

    data class SubModel(val text: String, val size: Int)

    fun syntax() {
        val subMutableDiffer = mutableLiveDiffer<SubModel> { /* live differ context */ }
        val subStatelessDiffer = statelessLiveDiffer<SubModel> { /* live differ context */ }

        val differ = mutableLiveDiffer<Model> {
            /* live differ context */

            value { it.date } call { /* called when "date" equals == false */ }
            ref { it.date } call { /* called when "date" references are different */ }
            any { it.date } call { /* called always after accept new model */ }
            value { it.sub.text } call { /* of course also possible select nested fields */ }

            // differ for field. inner field selectors will call only if "sub" changed
            // !!! differ instance also will clear, after clear "parent" differ
            value { it.sub } withMutableDiffer { /* live differ context */ }
            value { it.sub } withStatelessDiffer { /* live differ context */ }
            value { it.sub }.registerMutableDiffer(subMutableDiffer)
            value { it.sub }.registerStatelessDiffer(subStatelessDiffer)

            onClear { /* call when clear() differ */ }
        }

        val data = Model(Date(), SubModel("text1", 1))
        differ.accept(data)
        differ.clear()
    }

    fun basic() {
        val subMutableDiffer = mutableLiveDiffer<SubModel> {
            value { it.text } call {
                Log.d(TAG, "external mutable differ: call value Model.sub.text $it")
            }
            value { it.size } call {
                Log.d(TAG, "external mutable differ: call value Model.sub.size $it")
            }
            onClear {
                Log.d(TAG, "external mutable differ: clear")
            }
        }
        val subStatelessDiffer = statelessLiveDiffer<SubModel> {
            value { it.text } call {
                Log.d(TAG, "external stateless differ: call value Model.sub.text $it")
            }
            value { it.size } call {
                Log.d(TAG, "external stateless differ: call value Model.sub.size $it")
            }
            onClear {
                Log.d(TAG, "external stateless differ: clear")
            }
        }

        val differ = mutableLiveDiffer<Model> {

            // call when "date" equals == false
            value { it.date } call {
                Log.d(TAG, "call value Model.date $it")
            }

            // call when "date" references are different
            ref { it.date } call {
                Log.d(TAG, "call ref Model.date $it")
            }

            // call always after accept new model
            any { it.date } call {
                Log.d(TAG, "call any Model.date $it")
            }

            // of course also possible select nested fields
            value { it.sub.text } call {
                Log.d(TAG, "call value Model.sub.text $it")
            }
            value { it.sub.size } call {
                Log.d(TAG, "call value Model.sub.size $it")
            }

            // differ for field. inner field selectors will call only if "sub" changed
            value { it.sub } withMutableDiffer {
                value { it.text } call {
                    Log.d(TAG, "inner mutable differ: call value Model.sub.text $it")
                }
                value { it.size } call {
                    Log.d(TAG, "inner mutable differ: call value Model.sub.size $it")
                }
                onClear {
                    Log.d(TAG, "inner mutable differ: clear")
                }
            }
            value { it.sub } withStatelessDiffer {
                value { it.text } call {
                    Log.d(TAG, "inner stateless differ: call value Model.sub.text $it")
                }
                value { it.size } call {
                    Log.d(TAG, "inner stateless differ: call value Model.sub.size $it")
                }
                onClear {
                    Log.d(TAG, "inner stateless differ: clear")
                }
            }

            // also can add external differ instance
            // !!! differ instance also will clear, after clear "parent" differ
            value { it.sub }.registerMutableDiffer(subMutableDiffer)
            value { it.sub }.registerStatelessDiffer(subStatelessDiffer)

            // call when clear() differ
            onClear {
                Log.d(TAG, "call clear")
            }
        }

        val initialModel = Model(Date(), SubModel("text1", 1))
        val model2 = initialModel.copy(date = Date(0))
        val model3 = model2.copy(sub = model2.sub.copy(text = "text2"))

        Log.d(TAG, "sample: 1 accept")
        differ.accept(initialModel)

        Log.d(TAG, "sample: 2 accept")
        differ.accept(model2)

        Log.d(TAG, "sample: 3 accept")
        differ.accept(model3)

        Log.d(TAG, "sample: clear")
        differ.clear()
    }
}