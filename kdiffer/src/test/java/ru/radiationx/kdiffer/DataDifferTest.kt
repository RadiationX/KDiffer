package ru.radiationx.kdiffer

import org.junit.Test
import ru.radiationx.kdiffer.dsl.ext.any
import ru.radiationx.kdiffer.dsl.ext.call
import ru.radiationx.kdiffer.dsl.ext.ref
import ru.radiationx.kdiffer.dsl.ext.value
import java.util.*
import kotlin.system.measureTimeMillis

class DataDifferTest {


    private val defaultArticle = Article(
        id = 11,
        title = "Finally, amazing data-differ appear",
        date = Date(0),
        author = "Somedud",
        content = ArticleContent(
            body = "huge body",
            header = "smoll head",
            footer = "tall footer"
        ),
        tags = listOf("amzing", "diff"),
        source = null
    )

    @Test
    fun performance() {
        performanceKke()
        performanceKke()
        performanceKke()
        performanceKke()
        performanceKke()
    }
    @Test
    fun performanceKke() {
        val anotherArticle = Article(
            id = 11,
            title = "Finally, amazing data-differ appear",
            date = Date(0),
            author = "Somedud",
            content = ArticleContent(
                body = "huge body",
                header = "smoll head",
                footer = "tall footer"
            ),
            tags = listOf("amzing", "diff"),
            source = "src"
        )
        val startNano = System.nanoTime()
        val contentDiffer = mutableLiveDiffer<ArticleContent?> {
            value { it } call {}
            value { it?.body } call {}
            value { it?.header } call {}
            value { it?.footer } call {}

            ref { it } call {}
            ref { it?.body } call {}
            ref { it?.header } call {}
            ref { it?.footer } call {}

            any { it } call {}
            any { it?.body } call {}
            any { it?.header } call {}
            any { it?.footer } call {}
        }
        val articleDiffer = mutableLiveDiffer<Article> {
            value { it } call {}
            value { it.id } call {}
            value { it.title } call {}
            value { it.date } call {}
            value { it.author } call {}
            value { it.content } call {
                contentDiffer.accept(it)
            }
            value { it.tags } call {}
            value { it.source } call {}

            ref { it } call {}
            ref { it.id } call {}
            ref { it.title } call {}
            ref { it.date } call {}
            ref { it.author } call {}
            ref { it.content } call {
                contentDiffer.accept(it)
            }
            ref { it.tags } call {}
            ref { it.source } call {}

            any { it } call {}
            any { it.id } call {}
            any { it.title } call {}
            any { it.date } call {}
            any { it.author } call {}
            any { it.content } call {
                contentDiffer.accept(it)
            }
            any { it.tags } call {}
            any { it.source } call {}
        }
        val endNano = System.nanoTime()
        println("differ millis: ${(endNano - startNano).div(1000000.0)}")

        val articles = (0 until 10000).map {
            anotherArticle.run {
                copy(
                    id + it,
                    title + it,
                    Date(date.time + it),
                    author + it,
                    content?.run {
                        copy(
                            body + it,
                            header?.run { this + it },
                            footer?.run { this + it }
                        )
                    },
                    tags?.map { tag -> tag + it },
                    source?.run { this + it }
                )
            }
        }

        val warmTime = measureTimeMillis {
            (0 until 100).forEach {
                articleDiffer.accept(anotherArticle)
            }
        }

        val firstTime = measureTimeMillis {
            articles.forEach {
                articleDiffer.accept(it)
            }
        }


        val secondTime = measureTimeMillis {
            articles.forEach {
                articleDiffer.accept(it)
            }
        }
        println("measured time: $warmTime, $firstTime, $secondTime")
        println()
    }

    @Test
    fun performance1() {
        val anotherArticle = Article(
            id = 11,
            title = "Finally, amazing data-differ appear",
            date = Date(0),
            author = "Somedud",
            content = ArticleContent(
                body = "huge body",
                header = "smoll head",
                footer = "tall footer"
            ),
            tags = listOf("amzing", "diff"),
            source = "src"
        )

        val articleDiffer = mutableLiveDiffer<Article> {
            value { it.date } call {
                println("value: $it")
            }

            ref { it.date } call {
                println("ref: $it")
            }

            any { it.date } call {
                println("any: $it")
            }
        }

        println("INITIAL")
        articleDiffer.accept(anotherArticle)
        println()
        println()

        println("REAL")
        articleDiffer.apply {
            println("first_")
            accept(anotherArticle.copy(date = Date(0)))
            val refDate = Date(0)
            println("second_")
            accept(anotherArticle.copy(date = refDate))
            println("third_")
            accept(anotherArticle.copy(date = refDate))
        }

    }
}