package com.litobumba.translator_kmm.translate.presentation

import com.litobumba.translator_kmm.core.presentation.UiLanguage

data class UiHistoryItem (
    val id: Long?,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)