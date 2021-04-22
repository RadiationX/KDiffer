package ru.radiationx.kdiffer

import org.junit.Test
import ru.radiationx.kdiffer.dsl.ext.*

class DifferTests {

    @Test
    fun kekeke() {
        val liveDiffer = mutableLiveDiffer<Article> {
            value { it.source } call {
            }
            value { it.title } withStatelessDiffer {

            }
            value { it.title } withField {
                call {

                }
                withStatelessDiffer {

                }
                withMutableDiffer {

                }
            }
            onClear {

            }
        }

        val objectDiffer = mutableModelDiffer<Article> {
            val titleDiffer = mutableModelDiffer<String>({})
            value("source") { it.source } map {

            }

            value("kkeke") { it.title } withStatelessDiffer {

            }

            value("kekeke") { it.title }.registerMutableDiffer(titleDiffer)

            value("titile") { it.content } withStatelessDiffer {
                value("mapbody") { it?.body }
                value("mapbody") { it?.body }
                value("mapbody") { it?.body } withStatelessDiffer {
                    value("body") { it }
                }
            }
            onClear {
            }
        }
    }

    @Test
    fun kekeke1() {
        val liveDiffer = statelessLiveDiffer<Article> {
            value { it.source } call {
            }
            onClear {
            }
        }
        val objectDiffer = statelessModelDiffer<Article> {
            value("source") { it.source } map {
            }
            onClear {
            }
        }

    }
}