package com.litobumba.translator_kmm.di

import com.litobumba.translator_kmm.testing.FakeHistoryDataSource
import com.litobumba.translator_kmm.testing.FakeTranslateClient
import com.litobumba.translator_kmm.testing.FakeVoiceToTextParser
import com.litobumba.translator_kmm.translate.domain.translate.Translate

class TestAppModule : AppModule {

    override val historyDataSource = FakeHistoryDataSource()

    override val client = FakeTranslateClient()

    override val translateUseCase: Translate
        get() = Translate(
            client = client,
            historyDataSource = historyDataSource
        )

    override val voiceParser = FakeVoiceToTextParser()
}