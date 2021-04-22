package ru.radiationx.kdiffersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.radiationx.kdiffer.dsl.ext.any
import ru.radiationx.kdiffer.dsl.ext.call
import ru.radiationx.kdiffer.dsl.ext.value
import ru.radiationx.kdiffer.mutableLiveDiffer
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runDiffer()
    }

    data class AmazindData(
        val title: String,
        val date: Date? = null,
        val content: AmazingContent? = null
    )

    data class AmazingContent(
        val header: String? = null,
        val body: String? = null,
        val footer: String? = null
    )

    fun runDiffer() {

        val dataDiffer = mutableLiveDiffer<AmazindData> {
            any { it } call {
                Log.d("kek", "self updated: $it")
            }
            value { it.title } call { Log.d("kek", "title changed: $it") }
            value { it.content?.footer } call { Log.d("kek", "content.footer $it") }
        }

        val data = AmazindData("")
        data.component1()

        dataDiffer.accept(AmazindData("title1"))
        dataDiffer.clear()
        dataDiffer.accept(AmazindData("title1"))
        dataDiffer.accept(AmazindData("title1", Date()))
        dataDiffer.accept(AmazindData("title1", Date(), AmazingContent(body = "hello body")))
        dataDiffer.accept(
            AmazindData(
                "title1",
                Date(),
                AmazingContent(body = "hello body", footer = "hello footer")
            )
        )

    }
}