package com.translator_kmm.translate.data.remote

import com.litobumba.translator_kmm.core.domain.language.Language
import com.litobumba.translator_kmm.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {

    var translatedText = "test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}