package ru.radiationx.kdiffer

import java.util.*

data class Article(
    val id: Int,
    val title: String,
    val date: Date,
    val author: String,
    val content: ArticleContent?,
    val tags: List<String>?,
    val source: String?
)

data class ArticleContent(
    val body: String,
    val header: String?,
    val footer: String?
)


val defaultArticle = Article(
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
