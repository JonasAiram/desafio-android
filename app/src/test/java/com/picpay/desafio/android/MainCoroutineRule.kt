package com.picpay.desafio.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.picpay.desafio.android.presentation.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(
        TestCoroutineScheduler()
    )
) : TestWatcher() {

    val testDispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
class TestableObserver<T> : Observer<T> {
    private val history: MutableList<T> = mutableListOf()

    override fun onChanged(value: T) {
        history.add(value)
    }

    fun assertAllEmitted(values: List<T>) {
        assertEquals(values.count(), history.count())

        history.forEachIndexed { index, t ->
            assertEquals(values[index], t)
        }
    }
}
