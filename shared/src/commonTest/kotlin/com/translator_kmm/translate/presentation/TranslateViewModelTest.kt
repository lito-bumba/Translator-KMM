package com.translator_kmm.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.litobumba.translator_kmm.core.presentation.UiLanguage
import com.litobumba.translator_kmm.translate.domain.history.HistoryDataSource
import com.litobumba.translator_kmm.translate.domain.history.HistoryItem
import com.litobumba.translator_kmm.translate.domain.translate.Translate
import com.litobumba.translator_kmm.translate.domain.translate.TranslateClient
import com.litobumba.translator_kmm.translate.presentation.TranslateEvent
import com.litobumba.translator_kmm.translate.presentation.TranslateState
import com.litobumba.translator_kmm.translate.presentation.TranslateViewModel
import com.litobumba.translator_kmm.translate.presentation.UiHistoryItem
import com.translator_kmm.translate.data.local.FakeHistoryDataSource
import com.translator_kmm.translate.data.remote.FakeTranslateClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: HistoryDataSource

    @BeforeTest
    fun setUp() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translate = Translate(
            client = client,
            historyDataSource = dataSource
        )
        viewModel = TranslateViewModel(
            translate = translate,
            historyDataSource = dataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and history items are combine`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "de",
                toText = "to"
            )
            dataSource.insertHistoryItem(item)

            val state = awaitItem()

            val expeted = UiHistoryItem(
                item.id, item.fromText, item.toText,
                UiLanguage.byCode(item.fromLanguageCode),
                UiLanguage.byCode(item.toLanguageCode)
            )
            assertThat(state.history.first()).isEqualTo(expeted)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)
            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}