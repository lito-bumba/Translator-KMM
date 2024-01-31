package com.litobumba.translator_kmm.di

import com.litobumba.translator_kmm.database.TranslateDatabase
import com.litobumba.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.litobumba.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.litobumba.translator_kmm.translate.data.remote.HttpClientFactory
import com.litobumba.translator_kmm.translate.data.translate.KtorTranslateClient
import com.litobumba.translator_kmm.translate.domain.history.HistoryDataSource
import com.litobumba.translator_kmm.translate.domain.translate.Translate
import com.litobumba.translator_kmm.translate.domain.translate.TranslateClient
import com.litobumba.translator_kmm.voice_to_text.domain.VoiceToTextParser

interface AppModule {
    val historyDataSource: HistoryDataSource
    val client: TranslateClient
    val translateUseCase: Translate
    val voiceParser: VoiceToTextParser
}

class AppModuleImpl(
    parser: VoiceToTextParser
) : AppModule {

    override val historyDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    override val client: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    override val translateUseCase: Translate by lazy {
        Translate(
            client = client,
            historyDataSource = historyDataSource
        )
    }

    override val voiceParser = parser
}