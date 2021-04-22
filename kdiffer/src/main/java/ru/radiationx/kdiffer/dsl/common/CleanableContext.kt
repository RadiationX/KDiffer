package ru.radiationx.kdiffer.dsl.common

interface CleanableContext {

    fun onClear(block: () -> Unit)
}