package ru.radiationx.kdiffer

import org.junit.Test
import ru.radiationx.kdiffer.dsl.ext.call
import ru.radiationx.kdiffer.dsl.ext.map
import ru.radiationx.kdiffer.dsl.ext.withStatelessDiffer
import ru.radiationx.kdiffer.dsl.ext.value

class NewDiffersTest {


    @Test
    fun mapTest() {
        val differ = mutableModelDiffer<Article> {
            value("title_hash") { it.title } map { it.hashCode() }

            value("title") { it.title } withStatelessDiffer {
                value("hash") { it.hashCode() } map { "$it" }
                value("size") { it.length } withStatelessDiffer {
                    value("value") { it }
                }
            }

            value("content") { it.content } withStatelessDiffer {

            }
        }

        println("res: ${differ.accept(defaultArticle)}")
        println("res: ${differ.accept(defaultArticle.copy(title = "hui"))}")
    }

    @Test
    fun lololo1() {
        val liveDiffer = statelessModelDiffer<Article> {
            value("title") { it.title }
        }

        println(liveDiffer.accept(defaultArticle, defaultArticle.copy()))
        println(liveDiffer.accept(defaultArticle, defaultArticle.copy(title = "hello there")))
    }

    @Test
    fun lololo() {
        val liveDiffer = statelessLiveDiffer<Article> {
            value { it.title } call {


                println("liveDiffer: $it")
            }
        }

        liveDiffer.accept(defaultArticle, defaultArticle.copy(title = "hello there"))
        liveDiffer.accept(defaultArticle, defaultArticle.copy(title = "hello there"))
    }

    @Test
    fun keke0() {

        val objectDiffer = mutableModelDiffer<Article> {
            value("source") {
                println("valueChecker: ${old?.source} -> ${new.source}")
                it.source
            } map {
                println("map ${field.old} -> $it")
                it
            }
        }

        println("result: ${objectDiffer.accept(defaultArticle)}")
        println("result: ${objectDiffer.accept(defaultArticle.copy(source = "jej"))}")
        println("result: ${objectDiffer.accept(defaultArticle.copy(title = "or kek"))}")
        println("result: ${objectDiffer.accept(defaultArticle.copy(source = null))}")
    }

    @Test
    fun keke() {
        val contentDiffer = mutableModelDiffer<ArticleContent> {
            value("header") { it.header }
        }
        val objectDiffer = mutableModelDiffer<Article> {
            value("title") { it.title }
            value("date") { it.date }
            value("content") { it.content } map { content ->
                content?.let {
                    contentDiffer.accept(it).toMutableMap().apply {
                        put("cont.obj.prev", parent.old?.title)
                        put("cont.obj.curr", parent.new.title)
                    }
                }
            }
        }

        println("result: ${objectDiffer.accept(defaultArticle)}")
        println("result: ${objectDiffer.accept(defaultArticle.copy(title = "jej"))}")
        println(
            "result: ${
                objectDiffer.accept(
                    defaultArticle.copy(
                        title = "or kek",
                        content = defaultArticle.content?.copy(header = "aaaawtf")
                    )
                )
            }"
        )
    }

    @Test
    fun keke1() {
        val contentDiffer = mutableLiveDiffer<ArticleContent> {
            value { it.header } call {
                println("content.header $it")
            }
        }
        val objectDiffer = mutableLiveDiffer<Article> {
            value { it.title } call {
                println("title ${field.old} -> ${field.new}")
            }
            value { it.date } call {
                println("date $it")
            }
            value { it.content } call { content ->
                content?.let { contentDiffer.accept(it) }
            }
        }

        objectDiffer.accept(defaultArticle)
        objectDiffer.accept(defaultArticle.copy(title = "jej"))
        objectDiffer.accept(
            defaultArticle.copy(
                title = "or kek",
                content = defaultArticle.content?.copy(header = "aaaawtf")
            )
        )
    }
}